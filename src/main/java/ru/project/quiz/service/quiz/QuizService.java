package ru.project.quiz.service.quiz;

import org.springframework.web.bind.annotation.RequestParam;
import ru.project.quiz.domain.dto.quiz.QuizDTO;
import ru.project.quiz.domain.entity.quiz.Quiz;

public interface QuizService {
    QuizDTO createQuiz (int numberOfQuestions, String quizName);
    QuizDTO finishQuiz ( QuizDTO quizDTO);

    QuizDTO getQuizById(long id);
}
