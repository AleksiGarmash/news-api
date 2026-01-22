package com.example.news.mapper;

import com.example.news.model.Comment;
import com.example.news.model.News;
import com.example.news.service.NewsService;
import com.example.news.service.UserService;
import com.example.news.web.model.CategoryRequest;
import com.example.news.web.model.CommentRequest;
import com.example.news.web.model.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentMapperDelegate implements CommentMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService;

    @Override
    public Comment requestToComment(CommentRequest request) {
        Comment comment = new Comment();
        comment.setUser(userService.findById(request.getUserId()));
        comment.setNews(newsService.findById(request.getNewsId()));
        comment.setComment(request.getComment());

        return comment;
    }

    @Override
    public Comment requestToComment(Long commentId, CommentRequest request) {
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setComment(request.getComment());

        return comment;
    }
}
