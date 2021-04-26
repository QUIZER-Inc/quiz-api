package ru.project.quiz.controller.quiz;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.dto.quiz.CategoryDTO;
import ru.project.quiz.domain.entity.quiz.Category;
import ru.project.quiz.handler.response.Response;
import ru.project.quiz.mapper.quiz.CategoryMapper;
import ru.project.quiz.service.quiz.CategoryService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
@Tag(name = "Контроллер категорий")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    @Operation(summary = "Получение списка категорий", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return new ResponseEntity<>(categoryMapper.listCategoryDTOFromCategory(categoryService.getAllCategories()), HttpStatus.OK);
    }

    @GetMapping("/name/{categoryName}")
    @Operation(summary = "Получение одной категории", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String categoryName) {
        return new ResponseEntity<>(categoryMapper.categoryDTOFromCategory(categoryService.getCategory(categoryName)), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Получение одной категории", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable long id) {
        return new ResponseEntity<>(categoryMapper.categoryDTOFromCategory(categoryService.getById(id)), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Добавление новой категорий", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Response> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryMapper.categoryFromCategoryDTO(categoryDTO);
        categoryService.addCategory(category);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create("/api/categories/" + category.getId()));
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Изменение одной категорий", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CategoryDTO> editCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryMapper.categoryFromCategoryDTO(categoryDTO);
        categoryService.editCategory(category);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Operation(summary = "Удаление одной категории", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> deleteCategory(@RequestParam String categoryName) {
        categoryService.deleteCategory(categoryName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
