package com.example.news.repository;

import com.example.news.model.News;
import com.example.news.web.filter.NewsFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NewsSpecificationTest {

    @Test
    @DisplayName("withFilter: все null фильтры → Specification не null")
    void withFilter_allNull_specificationCreated() {
        NewsFilter filter = new NewsFilter();
        filter.setUserId(null);
        filter.setCategoryId(null);
        filter.setCategoriesId(null);

        Specification<News> spec = NewsSpecification.withFilter(filter);

        assertThat(spec).isNotNull();
    }

    @Test
    @DisplayName("withFilter: только userId → spec создаётся")
    void withFilter_onlyUserId_specCreated() {
        NewsFilter filter = new NewsFilter();
        filter.setUserId(1L);
        filter.setCategoryId(null);
        filter.setCategoriesId(null);

        Specification<News> spec = NewsSpecification.withFilter(filter);
        assertThat(spec).isNotNull();
    }

    @Test
    @DisplayName("withFilter: только categoryId → spec создаётся")
    void withFilter_onlyCategoryId_specCreated() {
        NewsFilter filter = new NewsFilter();
        filter.setUserId(null);
        filter.setCategoryId(5L);
        filter.setCategoriesId(null);

        Specification<News> spec = NewsSpecification.withFilter(filter);
        assertThat(spec).isNotNull();
    }

    @Test
    @DisplayName("withFilter: byCategoriesIds → spec создаётся")
    void withFilter_byCategoriesIds_specCreated() {
        NewsFilter filter = new NewsFilter();
        filter.setCategoriesId(List.of(1L, 2L, 3L));

        Specification<News> spec = NewsSpecification.withFilter(filter);
        assertThat(spec).isNotNull();
    }
}