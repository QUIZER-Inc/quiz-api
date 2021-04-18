package ru.project.quiz.service.quiz;

import ru.project.quiz.domain.dto.quiz.QuestionDTO;

import java.util.ArrayList;

public interface QuestionService {
    QuestionDTO getRandomQuestion();

    void saveQuestion(QuestionDTO questionDTO);

    void deleteQuestion(long id);

    void editQuestion(QuestionDTO questionDTO);

    void saveListofQuestions(ArrayList<QuestionDTO> questionDTOList);
}
