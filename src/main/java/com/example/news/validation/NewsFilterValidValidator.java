package com.example.news.validation;

import com.example.news.web.filter.NewsFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class NewsFilterValidValidator implements ConstraintValidator<NewsFilterValid, NewsFilter> {

    @Override
    public boolean isValid(NewsFilter filter, ConstraintValidatorContext constraintValidatorContext) {

        if (ObjectUtils.anyNull(filter.getPageNumber(), filter.getPageSize())) {
            return false;
        }

        if (filter.getCategoryId() != null && filter.getCategoriesId() != null) {
            return false;
        }

        return true;
    }
}
