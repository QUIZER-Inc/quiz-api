package ru.project.quiz.domain.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RatingDTO {

    private long id;

    private int passedQuiz;

    private int rightAnswers;
}
