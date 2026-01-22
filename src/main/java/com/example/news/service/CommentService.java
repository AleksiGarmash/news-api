package com.example.news.service;

import com.example.news.exception.UpdateStateException;
import com.example.news.model.Comment;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public interface CommentService {
    List<Comment> findAllByNews(Long newsId);
    Comment findById(Long id);
    Comment save(Comment comment);
    Comment update(Comment comment);
    void deleteById(Long id);

    default void checkForUpdate(Long commentId) {
        Comment currentComment = findById(commentId);
        Instant now = Instant.now();

        Duration duration = Duration.between(currentComment.getUpdatedAt(), now);

        if (duration.getSeconds() > 5) {
            throw new UpdateStateException("Unavailable to update Order!");
        }
    }
}
