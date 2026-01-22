package com.example.news.service.impl;

import com.example.news.exception.EntityNotFoundException;
import com.example.news.model.Category;
import com.example.news.repository.CategoryRepository;
import com.example.news.service.CategoryService;
import com.example.news.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Category with ID {} not found", id))
        );
    }

    @Override
    public Category save(Category category) {
        return repository.save(category);
    }

    @Override
    public Category update(Category category) {
        Category existedCategory = findById(category.getId());

        BeanUtils.copyNonNullProperties(category, existedCategory);

        return repository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
