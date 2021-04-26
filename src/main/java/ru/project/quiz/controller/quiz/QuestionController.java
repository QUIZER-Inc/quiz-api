package ru.project.quiz.controller.quiz;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.dto.quiz.QuestionDTO;
import ru.project.quiz.domain.dto.response.QuestionResponse;
import ru.project.quiz.domain.entity.quiz.Question;
import ru.project.quiz.handler.response.Response;
import ru.project.quiz.mapper.quiz.QuestionMapper;
import ru.project.quiz.service.quiz.QuestionService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/questions")
@Tag(name = "Контроллер вопросов")
public class QuestionController {

    private static final String RANDOM_QUESTION = "/random";

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @Operation(summary = "Получение рандом вопроса", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(RANDOM_QUESTION)
    public ResponseEntity<QuestionDTO> getRandomQuestion() {
        return new ResponseEntity<>(questionMapper.questionDTOFromQuestion(questionService.getRandomQuestion()), HttpStatus.OK);
    }

    @Operation(summary = "Получение рандом вопроса", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable long id) {
        return new ResponseEntity<>(questionMapper.questionDTOFromQuestion(questionService.getQuestionById(id)), HttpStatus.OK);
    }

    @Operation(summary = "Получение вопросов по категории", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getQuestionByCategory(@RequestParam(name = "category") String category) {
        return new ResponseEntity<>(questionMapper.listQuestionDTOFromQuestion(questionService.getQuestionByCategoryName(category)), HttpStatus.OK);
    }

    @Operation(summary = "Добавление списка вопросов", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<QuestionResponse> addListQuestion(@RequestBody ArrayList<Question> questionList) {
        QuestionResponse questionResponse = questionService.saveListQuestions(questionList);
        return new ResponseEntity<>(questionResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Удаление вопроса", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping
    public ResponseEntity<Response> deleteQuestion(@RequestParam(name = "questionId") long id) {
        questionService.deleteQuestion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Редактирование вопроса", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping
    public ResponseEntity<Response> editQuestion(@Valid @RequestBody QuestionDTO questionDTO) {
        Question question = questionMapper.questionFromQuestionDTO(questionDTO);
        questionService.editQuestion(question);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
