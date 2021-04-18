package ru.project.quiz.service.quiz.Impl;

import org.springframework.stereotype.Service;
import ru.project.quiz.domain.dto.quiz.CategoryDTO;
import ru.project.quiz.domain.entity.quiz.Category;
import ru.project.quiz.mapper.quiz.CategoryMapper;
import ru.project.quiz.repository.quiz.CategoryRepository;
import ru.project.quiz.service.quiz.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDTO> getAllCategoriesDTO() {
        return categoryMapper.listCategoryDTOFromCategory(categoryRepository.findAll());
    }

    public Category getCategory(String category) {
        return categoryRepository.findByName(category);
    }

    public CategoryDTO getCategoryDTO(String category) {
        return categoryMapper.categoryDTOFromCategory(categoryRepository.findByName(category));
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category editCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(String category) {
        categoryRepository.deleteByName(category);
    }

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }
}
