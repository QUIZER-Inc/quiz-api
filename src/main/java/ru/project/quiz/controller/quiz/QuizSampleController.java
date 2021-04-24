package ru.project.quiz.controller.quiz;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.entity.quiz.QuizSample;
import ru.project.quiz.handler.response.Response;
import ru.project.quiz.service.quiz.QuizSampleService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/quiz-sample")
@Tag(name = "Контроллер сэмплов")
public class QuizSampleController {
    public final QuizSampleService quizSampleService;

    @Operation(summary = "Добавление сэмпла", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<Response> addSample(@Valid @RequestBody QuizSample quizSample) {
        quizSampleService.saveSample(quizSample);
        return new ResponseEntity<>(new Response("Сэмпл добавлен"), HttpStatus.OK);
    }

    @Operation(summary = "Редактирование сэмпла", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping
    public ResponseEntity<Response> editQuestion(@Valid @RequestBody QuizSample quizSample, @RequestParam long id) {
        quizSampleService.editSample(quizSample, id);
        return new ResponseEntity<>(new Response("QuizSample has been edited"), HttpStatus.OK);
    }

    @Operation(summary = "Удаление сэмпла", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping
    public ResponseEntity<Response> deleteQuestion(@RequestParam long id) {
        quizSampleService.deleteSample(id);
        return new ResponseEntity<>(new Response("QuizSample has been deleted"), HttpStatus.NO_CONTENT);
    }

    public QuizSampleController(QuizSampleService quizSampleService) {
        this.quizSampleService = quizSampleService;
    }
}
