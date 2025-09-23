package com.upc.cicloestrella.mappers;

import com.upc.cicloestrella.DTOs.database.ReactionCountByDatabaseDTO;
import com.upc.cicloestrella.DTOs.requests.reviews.ReviewRequestDTO;
import com.upc.cicloestrella.DTOs.requests.reviews.ReviewUpdateRequestDTO;
import com.upc.cicloestrella.DTOs.responses.ReactionCountDTO;
import com.upc.cicloestrella.DTOs.responses.reviews.ReviewResponseDTO;
import com.upc.cicloestrella.DTOs.responses.reviews.StudentReviewResponseDTO;
import com.upc.cicloestrella.entities.Review;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewMapper {
    private final ModelMapper modelMapper ;
    private final StudentReviewMapper studentReviewMapper;

    @Autowired
    public ReviewMapper(ModelMapper modelMapper, StudentReviewMapper studentReviewMapper) {
        this.modelMapper = modelMapper;
        this.studentReviewMapper = studentReviewMapper;

        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public ReviewResponseDTO toDTO(Review review , List<ReactionCountByDatabaseDTO> reactionCountsFromDb){
        ReviewResponseDTO reviewResponseDTO = modelMapper.map(review, ReviewResponseDTO.class);
        StudentReviewResponseDTO studentDTO = studentReviewMapper.toDTO(review.getStudent());

        List<ReactionCountDTO> reactionCounts = reactionCountsFromDb.stream()
                .map(dbDto -> ReactionCountDTO.builder()
                        .id(dbDto.getReactionId())
                        .reactionName(dbDto.getReactionName())
                        .icon_url(dbDto.getIcon_url())
                        .count(dbDto.getCount())
                        .build()
                ).toList();

        reviewResponseDTO.setStudent(studentDTO);
        reviewResponseDTO.setReactions(reactionCounts);
        return reviewResponseDTO;
    }

    public Review toEntity(ReviewRequestDTO reviewRequestDTO){
        return modelMapper.map(reviewRequestDTO, Review.class);
    }

    public Review toEntity(ReviewUpdateRequestDTO reviewUpdateRequestDTO){
        return modelMapper.map(reviewUpdateRequestDTO, Review.class);
    }






}
