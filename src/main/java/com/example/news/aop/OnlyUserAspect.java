package com.example.news.aop;

import com.example.news.exception.NotAuthorException;
import com.example.news.model.Comment;
import com.example.news.model.News;
import com.example.news.model.User;
import com.example.news.service.CommentService;
import com.example.news.service.NewsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class OnlyUserAspect {

    private final NewsService newsService;
    private final CommentService commentService;

    @Before("@annotation(OnlyUser)")
    public void checkAuthor(JoinPoint joinPoint, OnlyUser onlyUser) {
        // 1. Извлекаем ID из пути /news/{id} или /comments/{id}
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            Map<String, String> pathVariables = (Map<String, String>)
                    request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            if (pathVariables != null && pathVariables.containsKey("id")) {
                Long entityId = Long.valueOf(pathVariables.get("id"));

                // 2. Определяем тип операции по пути запроса
                String requestPath = request.getRequestURI();

                if (requestPath.contains("/news/")) {
                    checkNewsAuthor(entityId);
                } else if (requestPath.contains("/comments/")) {
                    checkCommentAuthor(entityId);
                }
            }
        }
    }

    private void checkNewsAuthor(Long newsId) {
        News news = newsService.findById(newsId); // достаём новость
        User currentUser = getCurrentUser(); // получение текущего пользователя

        if (!news.getUser().getId().equals(currentUser.getId())) {
            throw new NotAuthorException("Только автор новости может её редактировать/удалять");
        }
    }

    private void checkCommentAuthor(Long commentId) {
        Comment comment = commentService.findById(commentId);
        User currentUser = getCurrentUser();

        if (!comment.getUser().getId().equals(currentUser.getId())) {
            throw new NotAuthorException("Только автор комментария может его редактировать/удалять");
        }
    }

    private User getCurrentUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String userIdParam = request.getParameter("userId");
            if (userIdParam != null) {
                User user = new User();
                user.setId(Long.valueOf(userIdParam));
                return user;
            }
        }
        throw new IllegalStateException("Текущий пользователь не определён");
    }
}
