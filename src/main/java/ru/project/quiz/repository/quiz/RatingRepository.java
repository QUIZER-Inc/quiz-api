package ru.project.quiz.repository.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.project.quiz.domain.entity.quiz.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

}
