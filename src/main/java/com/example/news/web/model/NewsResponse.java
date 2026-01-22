package com.example.news.web.model;

import com.example.news.model.Category;
import com.example.news.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsResponse {
    private Long id;
    private Long userId;
    private List<Category> categories;
    private String news;
    private List<Comment> comments;
}
