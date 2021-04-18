package ru.project.quiz.domain.dto.quiz;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.project.quiz.domain.enums.ituser.PermissionType;
import ru.project.quiz.domain.enums.question.CategoryType;
import ru.project.quiz.domain.enums.question.DifficultyType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Сущность вопроса")
public class QuestionDTO {
    @Schema(description = "ID", accessMode = Schema.AccessMode.READ_WRITE)
    long id;

    @NotBlank(message = "Название вопроса не должно быть пустым")
    @Schema(description = "Название")
    String name;

    @Schema(description = "Описание")
    String description;

    @Schema(description = "URL картинки")
    String imageUrl;

    @NotNull(message = "Сложность вопроса должна присутствовать")
    @Schema(description = "Сложность")
    DifficultyType difficultyType;

    private String categoryName;

    private Set<CategoryType> subCategories;

    @NotNull
    List<AnswerDTO> answers;
}
