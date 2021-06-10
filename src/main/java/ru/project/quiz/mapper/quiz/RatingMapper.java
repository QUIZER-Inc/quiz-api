package ru.project.quiz.mapper.quiz;

import org.mapstruct.Mapper;
import ru.project.quiz.domain.dto.quiz.RatingDTO;
import ru.project.quiz.domain.entity.quiz.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    Rating ratingFromRatingDTO(RatingDTO ratingDTO);
    RatingDTO ratingDTOFromRating(Rating rating);
}
