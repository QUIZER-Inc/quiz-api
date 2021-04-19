package ru.project.quiz.mapper.quiz;

import org.mapstruct.Mapper;
import ru.project.quiz.domain.dto.quiz.CategoryDTO;
import ru.project.quiz.domain.dto.quiz.QuestionDTO;
import ru.project.quiz.domain.entity.quiz.Category;
import ru.project.quiz.domain.entity.quiz.Question;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category categoryFromCategoryDTO(CategoryDTO categoryDTO);
    CategoryDTO categoryDTOFromCategory(Category category);
    List<Category> listCategoryFromCategoryDTO(List<CategoryDTO> categoryDTO);
    List<CategoryDTO> listCategoryDTOFromCategory(List<Category> category);
}
