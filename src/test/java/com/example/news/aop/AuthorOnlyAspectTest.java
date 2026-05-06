package com.example.news.aop;

import com.example.news.exception.NotAuthorException;
import com.example.news.model.Comment;
import com.example.news.model.News;
import com.example.news.model.User;
import com.example.news.service.CommentService;
import com.example.news.service.NewsService;
import com.example.news.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorOnlyAspectTest {

    @Mock private UserService userService;
    @Mock private NewsService newsService;
    @Mock private CommentService commentService;
    @Mock private JoinPoint joinPoint;
    @Mock private AuthorOnly authorOnly;
    @Mock private Authentication authentication;
    @Mock private SecurityContext securityContext;
    @Mock private HttpServletRequest httpRequest;
    @Mock private ServletRequestAttributes requestAttributes;

    @InjectMocks
    private AuthorOnlyAspect authorOnlyAspect;

    private User currentUser;

    @BeforeEach
    void setUp() {
        currentUser = new User();
        currentUser.setId(1L);
        currentUser.setName("alice");
    }

    private void mockSecurityAndRequest(String username, String uri, Long entityId) {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(username);
        when(userService.findByName(username)).thenReturn(currentUser);

        when(requestAttributes.getRequest()).thenReturn(httpRequest);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        when(httpRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE))
                .thenReturn(Map.of("id", entityId.toString()));
        when(httpRequest.getRequestURI()).thenReturn(uri);
    }

    @Test
    @DisplayName("checkAuthor: автор новости → проходит без исключения")
    void checkAuthor_newsAuthor_noException() {
        mockSecurityAndRequest("alice", "/api/news/1", 1L);

        News news = new News();
        news.setUser(currentUser);
        when(newsService.findById(1L)).thenReturn(news);

        assertThatCode(() -> authorOnlyAspect.checkAuthor(joinPoint, authorOnly))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("checkAuthor: не автор новости → NotAuthorException")
    void checkAuthor_notNewsAuthor_throwsNotAuthorException() {
        mockSecurityAndRequest("alice", "/api/news/1", 1L);

        User anotherUser = new User();
        anotherUser.setId(2L);

        News news = new News();
        news.setUser(anotherUser); // другой автор
        when(newsService.findById(1L)).thenReturn(news);

        assertThatThrownBy(() -> authorOnlyAspect.checkAuthor(joinPoint, authorOnly))
                .isInstanceOf(NotAuthorException.class)
                .hasMessageContaining("Only Author");
    }

    @Test
    @DisplayName("checkAuthor: автор комментария → проходит без исключения")
    void checkAuthor_commentAuthor_noException() {
        mockSecurityAndRequest("alice", "/api/comments/5", 5L);

        Comment comment = new Comment();
        comment.setUser(currentUser);
        when(commentService.findById(5L)).thenReturn(comment);

        assertThatCode(() -> authorOnlyAspect.checkAuthor(joinPoint, authorOnly))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("checkAuthor: не автор комментария → NotAuthorException")
    void checkAuthor_notCommentAuthor_throwsNotAuthorException() {
        mockSecurityAndRequest("alice", "/api/comments/5", 5L);

        User anotherUser = new User();
        anotherUser.setId(2L);

        Comment comment = new Comment();
        comment.setUser(anotherUser);
        when(commentService.findById(5L)).thenReturn(comment);

        assertThatThrownBy(() -> authorOnlyAspect.checkAuthor(joinPoint, authorOnly))
                .isInstanceOf(NotAuthorException.class);
    }
}