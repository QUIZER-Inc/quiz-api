package ru.project.quiz.service.quiz.Impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.project.quiz.domain.entity.ituser.ITUser;
import ru.project.quiz.domain.entity.quiz.Rating;
import ru.project.quiz.repository.quiz.RatingRepository;
import ru.project.quiz.service.quiz.RatingService;

@AllArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Override
    public void addUserToRating(ITUser itUser) {
        Rating rating = ratingRepository.getOne(0L);
        if (rating.equals(null)){
            createRating();
        }
        rating.setUser(itUser);
    }

    @Override
    public Rating createRating() {
        Rating rating = new Rating();
        ratingRepository.save(rating);
        return rating;
    }
}
