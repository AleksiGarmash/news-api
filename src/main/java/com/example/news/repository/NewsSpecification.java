package com.example.news.repository;

import com.example.news.model.News;
import com.example.news.web.filter.NewsFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface NewsSpecification {

    static Specification<News> withFilter(NewsFilter filter) {
        return Specification.where(byUserId(filter.getUserId()))
                .and(byCategoryId(filter.getCategoryId()))
                .and(byCategoriesIds(filter.getCategoriesId()));
    }

    static Specification<News> byUserId(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) return null;

            return criteriaBuilder.equal(root.get("user").get("id"), userId);
        };
    }

    static Specification<News> byCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) return null;

            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }

    static Specification<News> byCategoriesIds(List<Long> categoriesIds) {
        return (root, query, criteriaBuilder) -> {
            if (categoriesIds == null) return null;

            return criteriaBuilder.equal(root.get("categories").get("id"), categoriesIds);
        };
    }
}
