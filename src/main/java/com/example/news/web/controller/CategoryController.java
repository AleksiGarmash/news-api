package com.example.news.web.controller;

import com.example.news.mapper.CategoryMapper;
import com.example.news.model.Category;
import com.example.news.service.CategoryService;
import com.example.news.web.model.CategoryListResponse;
import com.example.news.web.model.CategoryRequest;
import com.example.news.web.model.CategoryResponse;
import com.example.news.web.model.CommentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news-service/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll() {
        return ResponseEntity.ok(
                categoryMapper.categoryListToCategory(categoryService.findAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                categoryMapper.categoryToResponse(categoryService.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryRequest request) {
        Category category = categoryService.save(categoryMapper.categoryToResponse(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.categoryToResponse(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody @Valid CategoryRequest request) {
        Category category = categoryService.update(categoryMapper.categoryToResponse(id, request));
        return ResponseEntity.ok(categoryMapper.categoryToResponse(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
