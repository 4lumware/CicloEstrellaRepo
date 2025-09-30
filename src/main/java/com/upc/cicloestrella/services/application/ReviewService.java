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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewService(StudentRepository studentRepository, TeacherRepository teacherRepository, TagRepository tagRepository, ReviewRepository reviewRepository, ReviewReactionRepository reviewReactionRepository, ReviewMapper reviewMapper) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.tagRepository = tagRepository;
        this.reviewRepository = reviewRepository;
        this.reviewReactionRepository = reviewReactionRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReviewResponseDTO save(ReviewRequestDTO reviewRequestDTO) {

        Long studentId = reviewRequestDTO.getStudentId();
        Long teacherId = reviewRequestDTO.getTeacherId();
        Student student = getStudentOrThrow(studentId);
        Teacher teacher = getTeacherOrThrow(teacherId);
        List<Tag> tags = getTagsOrThrow(reviewRequestDTO.getTagIds());
        Review newReview = reviewMapper.toEntity(reviewRequestDTO);

        newReview.setStudent(student);
        newReview.setTeacher(teacher);
        newReview.setTags(tags);

        Review savedReview = reviewRepository.save(newReview);
        List<ReactionCountByDatabaseDTO> reactionCounts = reviewReactionRepository.countAllReactionsByReview(List.of(savedReview));
        updateTeacherAverageRating(teacher);

        return reviewMapper.toDTO(savedReview, reactionCounts);
    }

    @Override
    public List<ReviewResponseDTO> index() {
        List<Review> reviews = reviewRepository.findAll();
        if (reviews.isEmpty()) {
            return List.of();
        }
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
    public ReviewResponseDTO show(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityIdNotFoundException("Reseña con id " + reviewId + " no encontrada"));
        List<ReactionCountByDatabaseDTO> reactionCounts = reviewReactionRepository.countAllReactionsByReview(List.of(review));
        return reviewMapper.toDTO(review, reactionCounts);
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByTeacher(Long teacherId , String keyword) {
        List<Review> reviews = (keyword == null || keyword.isEmpty()) ?
                reviewRepository.findReviewByTeacherId(teacherId) :
                reviewRepository.findTeacherByDescriptionOrTagName(teacherId, keyword);


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
        Student student = getStudentOrThrow(existingReview.getStudent().getId());
        Teacher teacher = getTeacherOrThrow(existingReview.getTeacher().getId());
        List<Tag> tags = getTagsOrThrow(reviewRequestDTO.getTagIds());
        existingReview.setStudent(student);
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
        if (!reviewRepository.existsById(reviewId)) {
            throw new EntityIdNotFoundException("Reseña con id " + reviewId + " no encontrada");
        }
        reviewRepository.deleteById(reviewId);
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
