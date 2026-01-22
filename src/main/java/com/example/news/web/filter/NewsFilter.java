package com.example.news.web.filter;

import com.example.news.validation.NewsFilterValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NewsFilterValid
public class NewsFilter {
    private Integer pageSize;
    private Integer pageNumber;
    private Long categoryId;
    private List<Long> categoriesId;
    private Long userId;
}
