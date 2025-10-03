package com.upc.cicloestrella.services.application;


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
import com.upc.cicloestrella.services.auth.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewReactionService implements ReviewReactionServiceInterface {
    private final ReviewReactionRepository reviewReactionRepository;
    private final ReactionRepository reactionRepository;
    private final StudentRepository studentRepository;
    private final ReviewRepository reviewRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final ModelMapper modelMapper;

    @Override
    public ReviewReactionResponseDTO addReactionToReview(Long reviewId, Long reviewReactionId) {

        Review review = getReviewOrThrow(reviewId);
        Reaction reaction = getReactionOrThrow(reviewReactionId);

        Student author = authenticatedUserService.getAuthenticatedStudent();

        if(existsThisAuthorInTheSameReaction(reviewId, author.getId(), reaction.getId())){
            throw new EntityIdNotFoundException("El autor con ID " + author.getId() + " ya ha reaccionado con la reacción con ID " + reaction.getId() + " a la reseña con ID " + reviewId);
        }

        ReviewReaction reviewReaction = ReviewReaction
                .builder()
                .review(review)
                .reaction(reaction)
                .author(author)
                .build();

        ReviewReaction savedReviewReaction =   reviewReactionRepository.save(reviewReaction);
        return modelMapper.map(savedReviewReaction , ReviewReactionResponseDTO.class);

    }


    @Override
    public ReviewReactionResponseDTO removeReactionFromReview(Long reviewId, Long reviewReactionId) {

        Student author = authenticatedUserService.getAuthenticatedStudent();

        if(!existReviewReactionWithReviewId(reviewId)){
            throw new EntityIdNotFoundException("No existe ninguna reacción asociada a la reseña con ID " + reviewId);
        }

        ReviewReaction reviewReaction = reviewReactionRepository.findById(reviewReactionId)
                .orElseThrow(() -> new EntityIdNotFoundException("Reacción de reseña no encontrada con ID " + reviewReactionId));

        if(!reviewReaction.getAuthor().getId().equals(author.getId())){
            throw new SecurityException("No tienes permiso para eliminar esta reacción de reseña");
        }

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
