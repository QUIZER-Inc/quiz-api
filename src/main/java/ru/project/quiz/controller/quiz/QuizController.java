package ru.project.quiz.controller.quiz;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.dto.quiz.QuizDTO;
import ru.project.quiz.service.quiz.QuizService;

@RestController
@Tag(name = "Контроллер тестов")
@RequestMapping("/api/quiz")
public class QuizController {
    private final QuizService quizService;

    @Operation(summary = "Создание квиза (теста)", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<QuizDTO> getQuiz(@RequestParam int numberOfQuestions,
                                           @RequestParam String quizName) {
        QuizDTO quizDTO = quizService.createQuiz(numberOfQuestions, quizName);
        return new ResponseEntity<>(quizDTO, HttpStatus.OK);
    }

    @Operation(summary = "Завершение квиза(теста)", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<QuizDTO> finishQuiz(@RequestBody QuizDTO quizDTO) {
        // HttpHeaders responseHeaders = new HttpHeaders();
        // responseHeaders.setLocation(URI.create("/api/quiz/" + quiz + "/"));
        //  return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        return new ResponseEntity<>(quizService.finishQuiz(quizDTO), HttpStatus.CREATED);
    }

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }
}
