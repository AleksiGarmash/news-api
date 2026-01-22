package com.example.news.service;

import com.example.news.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    Category update(Category category);
    void deleteById(Long id);
}
