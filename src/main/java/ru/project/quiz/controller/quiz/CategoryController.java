package ru.project.quiz.controller.quiz;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.entity.quiz.Category;
import ru.project.quiz.repository.quiz.CategoryRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Контроллер категорий")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    @Operation(summary = "Получение списка категорий", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{categoryName}")
    @Operation(summary = "Получение одной категории", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Category> getCategory(@PathVariable String categoryName) {
        return new ResponseEntity<>(categoryRepository.findByCategoryName(categoryName), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Добавление новой категорий", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Category> addCategory(@Valid @RequestBody Category category) {
        return new ResponseEntity<>(categoryRepository.save(category), HttpStatus.OK);
    }
    @PatchMapping
    @Operation(summary = "Изменение одной категорий", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Category> editCategory(@Valid @RequestBody Category category) {
        return new ResponseEntity<>(categoryRepository.save(category), HttpStatus.OK);
    }

    @DeleteMapping("/{categoryName}")
    @Operation(summary = "Удаление одной категории", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Category> deleteCategory(@PathVariable String categoryName) {
        categoryRepository.deleteByCategoryName(categoryName);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
