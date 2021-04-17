package ru.project.quiz.service.quiz.Impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ru.project.quiz.domain.dto.quiz.AnswerDTO;
import ru.project.quiz.domain.dto.quiz.QuestionDTO;
import ru.project.quiz.domain.entity.quiz.Question;
import ru.project.quiz.handler.exception.QuestionCreationException;
import ru.project.quiz.handler.exception.QuestionIsExistException;
import ru.project.quiz.handler.exception.QuestionNotFoundException;
import ru.project.quiz.mapper.quiz.AnswerMapper;
import ru.project.quiz.mapper.quiz.QuestionMapper;
import ru.project.quiz.repository.quiz.AnswerRepository;
import ru.project.quiz.repository.quiz.QuestionRepository;
import ru.project.quiz.service.quiz.QuestionService;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final Validator validator;

    Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionMapper questionMapper, AnswerRepository answerRepository, AnswerMapper answerMapper, Validator validator) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
        this.validator = validator;
    }

    @PostConstruct
    private void getAllTablesID() {
    }

    public QuestionDTO getRandomQuestion() {
        Question question = questionRepository.getRandomQuestion()
                .orElseThrow(() -> new QuestionNotFoundException("Question list is empty"));
        return questionMapper.questionDTOFromQuestion(question);
    }


    private final static String questionIsExistError = "Question {} is exist";
    private final static String sizeError = "Кол-во правильных ответов должно быть 1";
    private final static String correctError = "Кол-во вопросов должно быть 2 и более";//TODO надо ли делать проверку на количество входных вопросов


    public void saveQuestion(QuestionDTO questionDTO) {
        log.info("Попытка сохранить вопрос");
        Set<ConstraintViolation<QuestionDTO>> violations = validator.validate(questionDTO);

        if (!violations.isEmpty()) {
            log.error(violations.toString());
            throw new ConstraintViolationException(violations);
        }

        Question question = questionMapper.questionFromQuestionDTO(questionDTO);

        if (isExistQuestion(question)) {
            log.error(questionIsExistError);
            throw new QuestionIsExistException(questionIsExistError);
        }

        long countOfRightAnswers = questionDTO.getAnswers().stream()
                .map(AnswerDTO::isCorrectAnswer)
                .filter(correct -> correct)
                .count();

        if (countOfRightAnswers != 1 || questionDTO.getAnswers().size() < 2) {
            log.error(sizeError);
            throw new QuestionCreationException(sizeError);
        }

        question.setAnswers(answerMapper.listAnswersFromListAnswersDTO(questionDTO.getAnswers()));
        Question savedQuestion = questionRepository.save(question);
        log.info("Вопрос с id: {} сохранен", savedQuestion);
    }

    @Override
    public void saveQuestion(ArrayList<QuestionDTO> questionDtoList) {
        log.info("Попытка сохранить список вопросов");


        for (QuestionDTO dto:questionDtoList) {
            Set<ConstraintViolation<QuestionDTO>> validateSet = validator.validate(dto);
            if (!validateSet.isEmpty()) {
                log.error(validateSet.toString());
                throw new ConstraintViolationException(validateSet);
            }
        }

        List<QuestionDTO> dtoList = questionDtoList.stream()
                .filter(questionDTO -> {
                    if (!questionDTO.getAnswers().isEmpty()||questionDTO.getAnswers().size()<2){
                        return true;
                    } else {
                        log.info(sizeError);
                        return false;
                    }
                })
                .collect(Collectors.toList());

        HashSet<Question> questionSet = (HashSet<Question>) new HashSet<>(dtoList)
                .stream()
                .map(questionMapper::questionFromQuestionDTO)
                .filter(question -> {
                    if (!isExistQuestion(question)){
                        log.info(questionIsExistError, question.getName());
                    }
                    return isExistQuestion(question);
                })
                .collect(Collectors.toSet());

        questionSet.stream()
                .peek(question -> {
                    questionRepository.save(question);
                    log.info("Вопрос с id {} сохранен",question);
                })
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteQuestion(long id) {
        if (questionRepository.findById(id).isEmpty()) {
            throw new QuestionNotFoundException("Question not found with id: " + id);
        } else {
            questionRepository.deleteById(id);
        }
    }

    @Override
    public void editQuestion(QuestionDTO questionDTO) {
        questionRepository.findById(questionDTO.getId())
                .map(questionRepository::save)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found"));
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
