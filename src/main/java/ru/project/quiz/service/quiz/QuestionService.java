package ru.project.quiz.service.quiz;

import ru.project.quiz.domain.dto.quiz.QuestionDTO;

import java.util.Set;

public interface QuestionService {
    QuestionDTO getRandomQuestion();

    Set<QuestionDTO> getQuestionByCategoryName(String category);

    void saveQuestion(QuestionDTO questionDTO);

    void deleteQuestion(long id);

    void editQuestion(QuestionDTO questionDTO);
}
