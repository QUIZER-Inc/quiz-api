package ru.project.quiz.domain.dto.quiz;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.project.quiz.domain.enums.question.CategoryType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность категории")
public class CategoryDTO {

    private String name;

    private List<CategoryType> subCategories;
}
