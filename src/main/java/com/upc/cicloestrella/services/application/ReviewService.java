package com.upc.cicloestrella.services.application;

import com.upc.cicloestrella.DTOs.database.ReactionCountByDatabaseDTO;
import com.upc.cicloestrella.DTOs.requests.reviews.ReviewRequestDTO;
import com.upc.cicloestrella.DTOs.requests.reviews.ReviewUpdateRequestDTO;
import com.upc.cicloestrella.DTOs.responses.reviews.ReviewResponseDTO;
import com.upc.cicloestrella.entities.*;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.application.ReviewServiceInterface;
import com.upc.cicloestrella.mappers.ReviewMapper;
import com.upc.cicloestrella.repositories.interfaces.application.*;
import com.upc.cicloestrella.services.auth.AuthenticatedUserService;
import com.upc.cicloestrella.specifications.application.ReviewSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ReviewService implements ReviewServiceInterface {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final TagRepository tagRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewReactionRepository reviewReactionRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewService(StudentRepository studentRepository, TeacherRepository teacherRepository, TagRepository tagRepository, ReviewRepository reviewRepository, ReviewReactionRepository reviewReactionRepository, AuthenticatedUserService authenticatedUserService, ReviewMapper reviewMapper) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.tagRepository = tagRepository;
        this.reviewRepository = reviewRepository;
        this.reviewReactionRepository = reviewReactionRepository;
        this.authenticatedUserService = authenticatedUserService;
        this.reviewMapper = reviewMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReviewResponseDTO save(ReviewRequestDTO reviewRequestDTO) {
        Student authenticatedStudent = authenticatedUserService.getAuthenticatedStudent();
        Long studentId = authenticatedStudent.getId();
        Long teacherId = reviewRequestDTO.getTeacherId();
        Student student = getStudentOrThrow(studentId);
        Teacher teacher = getTeacherOrThrow(teacherId);
        List<Tag> tags = getTagsOrThrow(reviewRequestDTO.getTagIds());

        Review newReview = reviewMapper.toEntity(reviewRequestDTO);
        newReview.setId(null);
        newReview.setStudent(student);
        newReview.setTeacher(teacher);
        newReview.setTags(tags);

        Review savedReview = reviewRepository.save(newReview);
        List<ReactionCountByDatabaseDTO> reactionCounts = reviewReactionRepository.countAllReactionsByReview(List.of(savedReview));

        updateTeacherAverageRating(teacher);

        return reviewMapper.toDTO(savedReview, reactionCounts);
    }

    @Override
    public Page<ReviewResponseDTO> index(
            String keyword,
            Long studentId,
            Long teacherId,
            String teacherName,
            String studentName,
            BigDecimal minRating,
            BigDecimal maxRating,
            Long tagId,
            String tagName,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable
    ) {

        Specification<Review> reviews = ReviewSpecification.build(
                keyword, studentId, teacherId, teacherName, studentName,
                minRating, maxRating, tagId, tagName, from, to
        );

        Page<Review> reviewPage = reviewRepository.findAll(reviews, pageable);
        List<Review> reviewsInPage = reviewPage.getContent();

        List<ReactionCountByDatabaseDTO> allReactionsCountByDatabase =
                reviewReactionRepository.countAllReactionsByReview(reviewsInPage);
        

        Map<Long, List<ReactionCountByDatabaseDTO>> reactionsByReview = allReactionsCountByDatabase.stream()
                .collect(Collectors.groupingBy(ReactionCountByDatabaseDTO::getReviewId));

        return reviewPage.map(review -> {
            List<ReactionCountByDatabaseDTO> reactionCounts = reactionsByReview.getOrDefault(review.getId(), List.of());
            return reviewMapper.toDTO(review, reactionCounts);
        });
    }

    @Override
    public ReviewResponseDTO show(Long reviewId) {
        Review review = reviewRepository.findByIdAndStudent_User_StateTrue(reviewId)
                .orElseThrow(() -> new EntityIdNotFoundException("Reseña con id " + reviewId + " no encontrada"));
        
        List<ReactionCountByDatabaseDTO> reactionCounts = reviewReactionRepository.countAllReactionsByReview(List.of(review));
        return reviewMapper.toDTO(review, reactionCounts);
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByTeacher(Long teacherId , String keyword) {
        List<Review> reviews = (keyword == null || keyword.isEmpty()) ?
                reviewRepository.findReviewsByTeacherIdAndStudent_User_StateTrue(teacherId) :
                reviewRepository.findTeacherByDescriptionOrTagNameAndStudent_User_StateTrue(teacherId, keyword);

        List<ReactionCountByDatabaseDTO> allReactionsCountByDatabase = reviewReactionRepository.countAllReactionsByReview(reviews);

        Map<Long, List<ReactionCountByDatabaseDTO>> reactionsByReview = allReactionsCountByDatabase.stream()
                .collect(Collectors.groupingBy(ReactionCountByDatabaseDTO::getReviewId));

        return reviews.stream()
                .map(review -> {
                    List<ReactionCountByDatabaseDTO> reactionCounts = reactionsByReview.getOrDefault(review.getId(), List.of());
                    return reviewMapper.toDTO(review, reactionCounts);
                })
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReviewResponseDTO update(Long reviewId, ReviewUpdateRequestDTO reviewRequestDTO) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityIdNotFoundException("Reseña con id " + reviewId + " no encontrada"));

        Teacher teacher = getTeacherOrThrow(existingReview.getTeacher().getId());
        List<Tag> tags = getTagsOrThrow(reviewRequestDTO.getTagIds());

        existingReview.setTeacher(teacher);
        existingReview.setTags(tags);
        existingReview.setDescription(reviewRequestDTO.getDescription());
        existingReview.setRating(reviewRequestDTO.getRating());

        Review updatedReview = reviewRepository.save(existingReview);
        List<ReactionCountByDatabaseDTO> reactionCounts = reviewReactionRepository.countAllReactionsByReview(List.of(updatedReview));
        updateTeacherAverageRating(teacher);
        return reviewMapper.toDTO(updatedReview, reactionCounts);
    }

    @Override
    public void delete(Long reviewId) {

        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityIdNotFoundException("Reseña con id " + reviewId + " no encontrada"));
        reviewRepository.delete(existingReview);
        updateTeacherAverageRating(existingReview.getTeacher());
    }

    public Page<ReviewResponseDTO> findBySpecification(
            String keyword,
            Long studentId,
            Long teacherId,
            String teacherName,
            String studentName,
            BigDecimal minRating,
            BigDecimal maxRating,
            Long tagId,
            String tagName,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable) {

        Specification<Review> spec = ReviewSpecification.build(
                keyword, studentId, teacherId, teacherName, studentName,
                minRating, maxRating, tagId, tagName, from, to
        );

        Page<Review> reviews = reviewRepository.findAll(spec, pageable);

        List<Review> reviewList = reviews.getContent();
        List<ReactionCountByDatabaseDTO> allReactionsCountByDatabase =
                reviewReactionRepository.countAllReactionsByReview(reviewList);

        Map<Long, List<ReactionCountByDatabaseDTO>> reactionsByReview =
                allReactionsCountByDatabase.stream()
                .collect(Collectors.groupingBy(ReactionCountByDatabaseDTO::getReviewId));

        return reviews.map(review -> {
            List<ReactionCountByDatabaseDTO> reactionCounts =
                    reactionsByReview.getOrDefault(review.getId(), List.of());
            return reviewMapper.toDTO(review, reactionCounts);
        });
    }



    private void updateTeacherAverageRating(Teacher teacher) {
        BigDecimal averageRating = reviewRepository.findAverageRatingByTeacherId(teacher.getId());
        teacher.setAverageRating(averageRating != null ? averageRating : BigDecimal.ZERO);
        teacherRepository.save(teacher);
    }

    private Student getStudentOrThrow(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityIdNotFoundException("Estudiante con id " + studentId + " no encontrado"));
    }

    private Teacher getTeacherOrThrow(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityIdNotFoundException("Profesor con id " + teacherId + " no encontrado"));
    }

    private List<Tag> getTagsOrThrow(List<Long> tagIds) {
        List<Tag> tags = tagRepository.findAllById(tagIds);
        if (tags.size() != tagIds.size()) {
            throw new EntityIdNotFoundException("Algunos tags no fueron encontrados");
        }
        return tags;
    }
}
