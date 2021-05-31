package ru.project.quiz.service.quiz.Impl;

import org.springframework.stereotype.Service;
import ru.project.quiz.domain.entity.quiz.Category;
import ru.project.quiz.handler.exception.QuizAPPException;
import ru.project.quiz.mapper.quiz.CategoryMapper;
import ru.project.quiz.repository.quiz.CategoryRepository;
import ru.project.quiz.service.quiz.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(String category) {
        if (categoryRepository.findById(categoryRepository.findByName(category).getId()).isEmpty()){
            throw new QuizAPPException("Данной категории не существует");
        }
        return categoryRepository.findByName(category);
    }

    public Category addCategory(Category category) {
        if(categoryRepository.findById(category.getId()).isPresent()){
            throw new QuizAPPException("Категория с данным ID существует");
        }
        return categoryRepository.save(category);
    }

    public Category editCategory(Category category) {
        if(categoryRepository.findById(category.getId()).isEmpty()){
            throw new QuizAPPException("Категории с данным ID не существует");
        }
        return categoryRepository.save(category);
    }

    public void deleteCategory(String category) {
        if (categoryRepository.findById(categoryRepository.findByName(category).getId()).isEmpty()){
            throw new QuizAPPException("Данной категории не существует");
        }
        categoryRepository.deleteByName(category);
    }

    @Override
    public Category getById(long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()){
            throw new QuizAPPException("Категория с данным ID не существует");
        }
        return categoryOptional.get();
    }

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
    }
}
