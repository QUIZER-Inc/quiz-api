package ru.project.quiz.service.quiz;

import ru.project.quiz.domain.dto.quiz.QuestionDTO;

import java.util.ArrayList;
import java.util.HashSet;

public interface QuestionService {
    QuestionDTO getRandomQuestion();

    void saveQuestion(ArrayList<QuestionDTO> questionDtoSet);

    void deleteQuestion(long id);

    void editQuestion(QuestionDTO questionDTO);
}
