package com.example.news.mapper;

import com.example.news.model.Category;
import com.example.news.web.model.CategoryListResponse;
import com.example.news.web.model.CategoryRequest;
import com.example.news.web.model.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface CategoryMapper {
    Category categoryToResponse(CategoryRequest request);

    @Mapping(source = "categoryId", target = "id")
    Category categoryToResponse(Long categoryId, CategoryRequest request);

    CategoryResponse categoryToResponse(Category category);

    List<CategoryResponse> categoryListToResponseList(List<Category> categories);

    default CategoryListResponse categoryListToCategory(List<Category> categories) {
        CategoryListResponse response = new CategoryListResponse();
        response.setCategories(categoryListToResponseList(categories));

        return response;
    }
}
