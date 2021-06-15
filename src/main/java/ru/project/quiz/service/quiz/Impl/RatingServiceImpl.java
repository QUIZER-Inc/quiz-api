package ru.project.quiz.service.quiz.Impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.project.quiz.domain.entity.ituser.ITUser;
import ru.project.quiz.domain.entity.quiz.Rating;
import ru.project.quiz.handler.exception.QuizAPPException;
import ru.project.quiz.repository.quiz.RatingRepository;
import ru.project.quiz.service.quiz.RatingService;

import java.util.Optional;

@AllArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Override
    public void addUserToRating(ITUser itUser) {
        Rating rating = createRating();
        rating.setUser(itUser);
    }

    @Override
    public Rating createRating() {
        Rating rating = new Rating();
        ratingRepository.save(rating);
        return rating;
    }

    @Override
    public Rating getRatingByUserId(long id) {
        Optional<Rating> optRating = ratingRepository.getRatingByUserId(id);
        if (optRating.isEmpty()){
            throw new QuizAPPException("Рейтинг с данным User Id не существует");
        }
        return optRating.get();
    }

    @Override
    public void saveEditedRating(Rating rating) {
        ratingRepository.save(rating);
    }
}
