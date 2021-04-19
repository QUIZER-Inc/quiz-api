package ru.project.quiz.service.quiz;

import ru.project.quiz.domain.dto.quiz.QuestionDTO;
import ru.project.quiz.domain.dto.response.QuestionResponse;

import java.util.ArrayList;
import java.util.Set;

public interface QuestionService {
    QuestionDTO getRandomQuestion();

    Set<QuestionDTO> getQuestionByCategoryName(String category);

    int saveQuestion(QuestionDTO questionDTO);

    QuestionResponse saveListQuestions(ArrayList<QuestionDTO> questionDTOList);

    void deleteQuestion(long id);

    void editQuestion(QuestionDTO questionDTO);
}
