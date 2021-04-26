package ru.project.quiz.controller.quiz;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.dto.quiz.QuizSampleDTO;
import ru.project.quiz.domain.entity.quiz.QuizSample;
import ru.project.quiz.handler.response.Response;
import ru.project.quiz.mapper.quiz.QuizSampleMapper;
import ru.project.quiz.service.quiz.QuizSampleService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/quiz-sample")
@Tag(name = "Контроллер сэмплов")
public class QuizSampleController {
    private final QuizSampleService quizSampleService;
    private final QuizSampleMapper quizSampleMapper;

    @Operation(summary = "Добавление сэмпла", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<Response> addSample(@Valid @RequestBody QuizSampleDTO quizSampleDTO) {
        QuizSample quizSample = quizSampleMapper.quizSampleFromQuizSampleDto(quizSampleDTO);
        quizSampleService.saveSample(quizSample);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create("/api/quiz-sample/" + quizSample.getId() + "/"));
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @Operation(summary = "Получение сэмпла", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    public ResponseEntity<QuizSampleDTO> getSample(@PathVariable long id) {
        return new ResponseEntity<>(quizSampleMapper.quizSampleDTOFromQuizSample(quizSampleService.getSample(id)), HttpStatus.OK);
    }

    @Operation(summary = "Редактирование сэмпла", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping
    public ResponseEntity<String> editQuestion(@Valid @RequestBody QuizSampleDTO quizSampleDTO) {
        quizSampleService.editSample(quizSampleMapper.quizSampleFromQuizSampleDto(quizSampleDTO));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Удаление сэмпла", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping
    public ResponseEntity<String> deleteQuestion(@RequestParam long id) {
        quizSampleService.deleteSample(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
