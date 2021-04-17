package ru.project.quiz.domain.dto.quiz;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.project.quiz.domain.enums.question.CategoryType;

@Schema(description = "Сущность категории")
public class CategoryDTO {
    private String categoryName;

    private CategoryType category;

    public CategoryDTO(CategoryType category) {
        this.category = category;
    }

    public CategoryDTO() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }
}
