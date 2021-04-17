package ru.project.quiz.controller.quiz;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.dto.quiz.CategoryDTO;
import ru.project.quiz.domain.entity.quiz.Category;
import ru.project.quiz.handler.response.Response;
import ru.project.quiz.service.quiz.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Контроллер категорий")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Получение списка категорий", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategoriesDTO(), HttpStatus.OK);
    }

    @GetMapping("/{categoryName}")
    @Operation(summary = "Получение одной категории", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable String categoryName) {
        return new ResponseEntity<>(categoryService.getCategoryDTO(categoryName), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Добавление новой категорий", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Response> addCategory(@Valid @RequestBody Category category) {
        categoryService.addCategory(category);
        return new ResponseEntity<>(new Response("Category is added"), HttpStatus.CREATED);
    }

    @PatchMapping
    @Operation(summary = "Изменение одной категорий", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Response> editCategory(@Valid @RequestBody Category category) {
        categoryService.editCategory(category);
        return new ResponseEntity<>(new Response("Category edited"), HttpStatus.OK);
    }

    @DeleteMapping("/{categoryName}")
    @Operation(summary = "Удаление одной категории", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity deleteCategory(@PathVariable String categoryName) {
        categoryService.deleteCategory(categoryName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
