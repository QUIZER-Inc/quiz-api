package ru.project.quiz.controller.quiz;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.dto.quiz.QuestionDTO;
import ru.project.quiz.domain.dto.response.QuestionResponse;
import ru.project.quiz.handler.response.Response;
import ru.project.quiz.service.quiz.QuestionService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Set;

@RestController
@RequestMapping("/api/questions")
@Tag(name = "Контроллер вопросов")
public class QuestionController {

    private static final String RANDOM_QUESTION = "/random";

    private final QuestionService questionService;

    @Operation(summary = "Получение рандом вопроса", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(RANDOM_QUESTION)
    public ResponseEntity<QuestionDTO> getQuestion() {
        QuestionDTO questionDTO = questionService.getRandomQuestion();
        return new ResponseEntity<>(questionDTO, HttpStatus.OK);
    }

    @Operation(summary = "Получение вопросов по категории", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<Set<QuestionDTO>> getQuestionByCategory(@RequestParam(name = "category") String category) {
        return new ResponseEntity<>(questionService.getQuestionByCategoryName(category), HttpStatus.OK);
    }

    @Operation(summary = "Добавление списка вопросов", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<QuestionResponse> addListQuestion(@RequestBody ArrayList<QuestionDTO> questionDTOList) {
        QuestionResponse questionResponse = questionService.saveListQuestions(questionDTOList);
        return new ResponseEntity<>(questionResponse, HttpStatus.OK);
    }

    @Operation(summary = "Удаление вопроса", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping
    public ResponseEntity<Response> deleteQuestion(@RequestParam(name = "questionId") long id) {
        questionService.deleteQuestion(id);
        return new ResponseEntity<>(new Response("Question has been deleted"), HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Редактирование вопроса", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping
    public ResponseEntity<Response> editQuestion(@Valid @RequestBody QuestionDTO questionDTO) {
        questionService.editQuestion(questionDTO);
        return new ResponseEntity<>(new Response("Question has been edited"), HttpStatus.OK);
    }

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
}
