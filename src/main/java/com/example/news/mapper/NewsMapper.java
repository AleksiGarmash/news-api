package com.example.news.mapper;

import com.example.news.model.News;
import com.example.news.web.model.NewsListResponse;
import com.example.news.web.model.NewsResponseForList;
import com.example.news.web.model.NewsRequest;
import com.example.news.web.model.NewsResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface NewsMapper {
    News requestToNews(NewsRequest request);

    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, NewsRequest request);

    NewsResponse newsToResponse(News news);

    List<NewsResponseForList> newsListToResponseList(List<News> newses);

    default NewsListResponse newsListToNews(List<News> newses) {
        NewsListResponse response = new NewsListResponse();
        response.setNewses(newsListToResponseList(newses));

        return response;
    }
}
