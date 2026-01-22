package com.example.news.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsRequest {
    private Long userId;
    private List<Long> categoryIds;
    private String news;
}
