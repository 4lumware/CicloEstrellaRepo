package com.upc.cicloestrella.services.application;


import com.upc.cicloestrella.DTOs.requests.reviews.ReviewCreateReactionRequestDTO;
import com.upc.cicloestrella.DTOs.responses.reviews.ReviewReactionResponseDTO;
import com.upc.cicloestrella.entities.Reaction;
import com.upc.cicloestrella.entities.Review;
import com.upc.cicloestrella.entities.ReviewReaction;
import com.upc.cicloestrella.entities.Student;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.application.ReviewReactionServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.application.ReactionRepository;
import com.upc.cicloestrella.repositories.interfaces.application.ReviewReactionRepository;
import com.upc.cicloestrella.repositories.interfaces.application.ReviewRepository;
import com.upc.cicloestrella.repositories.interfaces.application.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ReviewReactionService implements ReviewReactionServiceInterface {
    private final ReviewReactionRepository reviewReactionRepository;
    private final ReactionRepository reactionRepository;
    private final StudentRepository studentRepository;
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    public ReviewReactionService(ReviewReactionRepository reviewReactionRepository, ReactionRepository reactionRepository, StudentRepository studentRepository, ReviewRepository reviewRepository, ModelMapper modelMapper) {
        this.reviewReactionRepository = reviewReactionRepository;
        this.reactionRepository = reactionRepository;
        this.studentRepository = studentRepository;
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

    // TODO: Implementar Spring Security para que solo un usuario autenticado pueda agregar una reacción

    @Override
    public ReviewReactionResponseDTO addReactionToReview(ReviewCreateReactionRequestDTO reviewReactionRequestDTO , Long reviewId) {
        Review review = getReviewOrThrow(reviewId);
        Reaction reaction = getReactionOrThrow(reviewReactionRequestDTO.getReactionId());
        Student author = getStudentOrThrow(reviewReactionRequestDTO.getAuthorId());

        if(existsThisAuthorInTheSameReaction(reviewId, author.getId(), reaction.getId())){
            throw new EntityIdNotFoundException("El autor con ID " + author.getId() + " ya ha reaccionado con la reacción con ID " + reaction.getId() + " a la reseña con ID " + reviewId);
        }

        ReviewReaction reviewReaction = modelMapper.map(reviewReactionRequestDTO, ReviewReaction.class);

        reviewReaction.setReview(review);
        reviewReaction.setReaction(reaction);
        reviewReaction.setAuthor(author);

        ReviewReaction savedReviewReaction =   reviewReactionRepository.save(reviewReaction);
        return modelMapper.map(savedReviewReaction , ReviewReactionResponseDTO.class);

    }

    // TODO: Implementar Spring Security para que solo el autor pueda eliminar su reacción

    @Override
    public ReviewReactionResponseDTO removeReactionFromReview(Long reviewId, Long reviewReactionId) {

        if(!existReviewReactionWithReviewId(reviewId)){
            throw new EntityIdNotFoundException("No existe ninguna reacción asociada a la reseña con ID " + reviewId);
        }

        ReviewReaction reviewReaction = reviewReactionRepository.findById(reviewReactionId)
                .orElseThrow(() -> new EntityIdNotFoundException("Reacción de reseña no encontrada con ID " + reviewReactionId));

        reviewReactionRepository.delete(reviewReaction);

        return modelMapper.map(reviewReaction , ReviewReactionResponseDTO.class);
    }

    private boolean existReviewReactionWithReviewId(Long reviewId) {
        return reviewReactionRepository.existsReviewReactionByReview_Id(reviewId);
    }

    private Review getReviewOrThrow(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityIdNotFoundException("Reseña no encontrada con ID " + reviewId));

    }
    private boolean existsThisAuthorInTheSameReaction(Long reviewId,  Long authorId, Long reactionId) {
        return reviewReactionRepository.existsReviewReactionByReview_IdAndAuthor_IdAndReaction_Id(reviewId, authorId, reactionId);
    }
    private Reaction getReactionOrThrow(Long reactionId) {
        return reactionRepository.findById(reactionId)
                .orElseThrow(() -> new EntityIdNotFoundException("Reacción no encontrada con ID " + reactionId));
    }

    private Student getStudentOrThrow(Long authorId) {
        return studentRepository.findById(authorId)
                .orElseThrow(() -> new EntityIdNotFoundException("Estudiante no encontrado con ID " + authorId));
    }


}
