package ru.project.quiz.service.quiz;

import ru.project.quiz.domain.dto.quiz.QuestionDTO;
import ru.project.quiz.domain.dto.response.QuestionResponse;
import ru.project.quiz.domain.entity.quiz.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface QuestionService {
    Question getRandomQuestion();

    List<Question> getQuestionByCategoryName(String category);

    int saveQuestion(Question question); //TODO need refactor

    QuestionResponse saveListQuestions(ArrayList<Question> questionList);

    void deleteQuestion(long id);

    Question editQuestion(Question question);

    Question getQuestionById(long id);
}
