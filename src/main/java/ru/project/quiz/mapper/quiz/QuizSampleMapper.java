package ru.project.quiz.mapper.quiz;

import org.mapstruct.Mapper;
import ru.project.quiz.domain.dto.quiz.QuizSample;

@Mapper(componentModel = "spring")
public interface QuizSampleMapper {
    ru.project.quiz.domain.entity.quiz.QuizSample quizSampleFromQuizSampleDto(QuizSample quizSample);
}
