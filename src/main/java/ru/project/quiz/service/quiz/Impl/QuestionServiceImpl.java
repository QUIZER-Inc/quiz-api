package ru.project.quiz.service.quiz.Impl;

import org.hibernate.mapping.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ru.project.quiz.domain.dto.response.QuestionResponse;
import ru.project.quiz.domain.entity.quiz.Answer;
import ru.project.quiz.domain.entity.quiz.Question;
import ru.project.quiz.handler.exception.QuizAPPException;
import ru.project.quiz.repository.quiz.AnswerRepository;
import ru.project.quiz.repository.quiz.QuestionRepository;
import ru.project.quiz.service.quiz.QuestionService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final Validator validator;
    private long count;

    Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository, Validator validator) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.validator = validator;
        count = 0L;
    }

    public Question getRandomQuestion() {
        return questionRepository.getRandomQuestion()
                .orElseThrow(() -> new QuizAPPException("Question list is empty"));
    }

    public List<Question> getQuestionByCategoryName(String categoryName) {
        return questionRepository.getQuestionsByCategoryName(categoryName);
    }

    private final static String questionIsExistError = "Question is exist";
    private final static String correctOrSizeError = "Кол-во правильных ответов должно быть 1, а вопросов 2 и больше";

    @Override
    /**
     * Возвращает статус код вопроса при сохранении
     * 0 - вопрос сохранен без ошибок
     * 1 - вопрос не сохранен (повторяющийся)
     * 2 - вопрос вызвал ошибку
     */
    public int saveQuestion(Question question) {
        log.info("Попытка сохранить вопрос");
        Set<ConstraintViolation<Question>> violations = validator.validate(question);
        if (!violations.isEmpty()) {
            log.error(violations.toString());
            return 2;
        }
        if (isExistQuestion(question)) {
            log.error(questionIsExistError);
            return 1;
        }
        long countOfRightAnswers = question.getAnswers().stream()
                .map(Answer::isCorrectAnswer)
                .filter(correct -> correct)
                .count();
        if (countOfRightAnswers != 1 || question.getAnswers().size() < 2) {
            log.error(correctOrSizeError);
            return 2;
        }
        question.setAnswers(question.getAnswers());
        Question savedQuestion = questionRepository.save(question);
        log.info("Вопрос с id: {} сохранен", savedQuestion.getId());
        return 0;
    }

    @Override
    public QuestionResponse saveListQuestions(ArrayList<Question> questionList) {
        QuestionResponse questionResponse = new QuestionResponse();
        questionList.forEach(question -> {
            int statusCode = saveQuestion(question);
            questionResponse.incrementByStatusCode(statusCode);
        });
        return questionResponse;
    }

    @Override
    public void deleteQuestion(long id) {
        if (questionRepository.findById(id).isEmpty()) {
            throw new QuizAPPException("Question not found with id: " + id);
        } else {
            questionRepository.deleteById(id);
        }
    }

    @Override
    public Question editQuestion(Question question) {
        questionRepository.findById(question.getId())
                .map(questionRepository::save)
                .orElseThrow(() -> new QuizAPPException("Question not found"));
        return question;
    }

    @Override
    public Question getQuestionById(long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if(optionalQuestion.isEmpty()){
            throw new QuizAPPException("Вопроса с данным ID не существует");
        }
        Question question = optionalQuestion.get();
        Collections.shuffle(question.getAnswers());
        return question;
    }

    private boolean isExistQuestion(Question question) {
        Example<Question> example = Example.of(question, modelToCheckExistQuestion());
        return questionRepository.exists(example);
    }

    private ExampleMatcher modelToCheckExistQuestion() {
        return ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnoreCase("name", "description", "imageUrl");
    }

}
