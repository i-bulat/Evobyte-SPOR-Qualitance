package com.evo.qualitanceProject.converter;

import com.evo.qualitanceProject.dto.ReviewDto;
import com.evo.qualitanceProject.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter extends BaseConverter<Review, ReviewDto> {
    @Override
    public ReviewDto convertModelToDto(Review review) {
        ReviewDto dto = ReviewDto.builder()
                .title(review.getTitle())
                .username(review.getUser().getUsername())
                .comment(review.getComment())
                .dateCreated(review.getDateCreated())
                .rating(review.getRating())
                .build();
        dto.setId(review.getId());
        return dto;
    }

    @Override
    public Review convertDtoToModel(ReviewDto dto) {
        throw new RuntimeException("not yet implemented.");
    }
}
