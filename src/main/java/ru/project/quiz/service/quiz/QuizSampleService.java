package ru.project.quiz.service.quiz;

import ru.project.quiz.domain.entity.quiz.QuizSample;

public interface QuizSampleService {
    QuizSample saveSample(QuizSample quizSample);

    QuizSample editSample(QuizSample quizSample, long id);

    void deleteSample(long id);
}
