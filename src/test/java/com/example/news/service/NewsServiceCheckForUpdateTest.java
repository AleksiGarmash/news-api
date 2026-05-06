package com.example.news.service;

import com.example.news.exception.UpdateStateException;
import com.example.news.model.News;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewsServiceCheckForUpdateTest {

    @Test
    @DisplayName("checkForUpdate: updatedAt < 5 секунд назад → без исключения")
    void checkForUpdate_recentUpdate_noException() {
        NewsService service = spy(createNewsService());
        News news = new News();
        news.setUpdatedAt(Instant.now().minusSeconds(2)); // 2 секунды назад
        when(service.findById(1L)).thenReturn(news);

        assertThatCode(() -> service.checkForUpdate(1L)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("checkForUpdate: updatedAt > 5 секунд назад → UpdateStateException")
    void checkForUpdate_oldUpdate_throwsUpdateStateException() {
        NewsService service = spy(createNewsService());
        News news = new News();
        news.setUpdatedAt(Instant.now().minusSeconds(10)); // 10 секунд назад
        when(service.findById(1L)).thenReturn(news);

        assertThatThrownBy(() -> service.checkForUpdate(1L))
                .isInstanceOf(UpdateStateException.class)
                .hasMessageContaining("Unavailable to update");
    }

    @Test
    @DisplayName("checkForUpdate: ровно 5 секунд → НЕ бросает (граница исключительная)")
    void checkForUpdate_exactly5seconds_noException() {
        NewsService service = spy(createNewsService());
        News news = new News();
        news.setUpdatedAt(Instant.now().minusSeconds(5));
        when(service.findById(1L)).thenReturn(news);

        // duration.getSeconds() > 5 → ровно 5 не бросает
        assertThatCode(() -> service.checkForUpdate(1L)).doesNotThrowAnyException();
    }


    private NewsService createNewsService() {
        return new NewsService() {
            public java.util.List<News> filterBy(com.example.news.web.filter.NewsFilter f) { return null; }
            public java.util.List<News> findAll() { return null; }
            public News findById(Long id) { return null; }
            public News save(News news) { return null; }
            public News update(News news) { return null; }
            public void deleteById(Long id) {}
        };
    }
}