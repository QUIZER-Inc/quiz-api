package ru.project.quiz.controller.quiz;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.dto.quiz.RatingDTO;
import ru.project.quiz.mapper.quiz.RatingMapper;
import ru.project.quiz.service.quiz.RatingService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rating")
@Tag(name = "Контроллер рейтинга")
public class RatingController {

    private final RatingService ratingService;
    private final RatingMapper ratingMapper;

    @Operation(summary = "Создание рейтинга", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<RatingDTO> createRating() {
        RatingDTO rating = ratingMapper.ratingDTOFromRating(ratingService.createRating());
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }
}
