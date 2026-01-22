package com.example.news.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OnlyUserAspect {

    @Before("@annotation(OnlyUser)")
    public void updateOnlyForUser() {

    }
}
