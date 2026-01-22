package com.example.news.service.impl;

import com.example.news.exception.EntityNotFoundException;
import com.example.news.model.News;
import com.example.news.model.User;
import com.example.news.repository.NewsRepository;
import com.example.news.repository.NewsSpecification;
import com.example.news.repository.UserRepository;
import com.example.news.service.NewsService;
import com.example.news.util.BeanUtils;
import com.example.news.web.filter.NewsFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final UserRepository userRepository;
    private final NewsRepository newsRepository;

    @Override
    public List<News> filterBy(NewsFilter filter) {
        return newsRepository.findAll(
                NewsSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize()))
                .getContent();
    }

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("News with ID {} not found", id))
        );
    }

    @Override
    public News save(News news) {
        Long userId = news.getUser().getId();
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("User with ID {} not found", userId)));
        news.setUser(user);

        return newsRepository.save(news);
    }

    @Override
    public News update(News news) {
        checkForUpdate(news.getId());

        Long userId = news.getUser().getId();
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("User with ID {} not found", userId)));

        News existedNews = findById(news.getId());
        BeanUtils.copyNonNullProperties(news, existedNews);
        news.setUser(user);

        return newsRepository.save(existedNews);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
}
