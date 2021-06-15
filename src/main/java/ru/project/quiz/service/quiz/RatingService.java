package ru.project.quiz.service.quiz;

import ru.project.quiz.domain.entity.ituser.ITUser;
import ru.project.quiz.domain.entity.quiz.Rating;

public interface RatingService {
    void addUserToRating(ITUser itUser);
    Rating createRating();
    Rating getRatingByUserId(long id);
    void saveEditedRating(Rating rating);
}
