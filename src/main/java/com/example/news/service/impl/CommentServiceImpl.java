package com.example.news.service.impl;

import com.example.news.exception.EntityNotFoundException;
import com.example.news.model.Comment;
import com.example.news.model.News;
import com.example.news.model.User;
import com.example.news.repository.CommentRepository;
import com.example.news.repository.NewsRepository;
import com.example.news.repository.UserRepository;
import com.example.news.service.CommentService;
import com.example.news.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    @Override
    public List<Comment> findAllByNews(Long newsId) {
        News news = newsRepository.findById(newsId).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("News with ID not found", newsId))
        );

        return commentRepository.findAllByNews(news);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Comment with ID {} not found", id))
        );
    }

    @Override
    public Comment save(Comment comment) {
        Long userId = comment.getUser().getId();
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("User with ID {} not found", userId)));
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        checkForUpdate(comment.getId());

        Long userId = comment.getUser().getId();
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("User with ID {} not found", userId)));

        Comment existedComment = findById(comment.getId());
        BeanUtils.copyNonNullProperties(comment, existedComment);
        comment.setUser(user);

        return commentRepository.save(existedComment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
