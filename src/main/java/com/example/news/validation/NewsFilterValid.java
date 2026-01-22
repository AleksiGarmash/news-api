package com.example.news.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NewsFilterValidValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NewsFilterValid {

    String message() default "The pagination fields must be specified! Only one of the filters should be specified: only one category or a list of categories!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
