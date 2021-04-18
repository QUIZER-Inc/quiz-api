package ru.project.quiz.service.quiz;

import ru.project.quiz.domain.dto.quiz.CategoryDTO;
import ru.project.quiz.domain.entity.quiz.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategoriesDTO();

    Category getCategory(String category);

    CategoryDTO getCategoryDTO(String category);

    Category addCategory(Category category);

    Category editCategory(Category category);

    void deleteCategory(String category);
}
