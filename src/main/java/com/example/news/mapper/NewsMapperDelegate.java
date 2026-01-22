package com.example.news.mapper;

import com.example.news.model.Category;
import com.example.news.model.News;
import com.example.news.service.CategoryService;
import com.example.news.service.UserService;
import com.example.news.web.model.NewsResponseForList;
import com.example.news.web.model.NewsRequest;
import com.example.news.web.model.NewsResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public News requestToNews(NewsRequest request) {
        News news = new News();
        news.setUser(userService.findById(request.getUserId()));
        news.setCategories(fromIds(request.getCategoryIds()));
        news.setNews(request.getNews());

        return news;
    }

    @Override
    public News requestToNews(Long newsId, NewsRequest request) {
        News news = new News();
        news.setId(newsId);
        news.setCategories(fromIds(request.getCategoryIds()));
        news.setNews(request.getNews());

        return null;
    }

    @Override
    public NewsResponse newsToResponse(News news) {
        return NewsResponse.builder()
                .id(news.getId())
                .userId(news.getUser().getId())
                .categories(news.getCategories())
                .news(news.getNews())
                .comments(news.getComments())
                .build();
    }

    @Override
    public List<NewsResponseForList> newsListToResponseList(List<News> newses) {
        List<NewsResponseForList> responseList = new ArrayList<>();

        for (News news : newses) {
            responseList.add(NewsResponseForList.builder()
                    .id(news.getId())
                    .userId(news.getUser().getId())
                    .categories(news.getCategories())
                    .news(news.getNews())
                    .commentsCount(news.getComments().size())
                    .build());
        }
        return responseList;
    }

    private Category fromId(Long id) {
        return id == null ? null : categoryService.findById(id);
    }

    private List<Category> fromIds(List<Long> ids) {
        return ids == null ? List.of() :
                ids.stream()
                        .map(this::fromId)
                        .filter(Objects::nonNull)
                        .toList();
    }
}
