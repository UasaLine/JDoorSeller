package com.jds.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("hello3");
        registry.addViewController("/").setViewName("hello3");
        registry.addViewController("/hello").setViewName("hello3");
        registry.addViewController("/login").setViewName("login2");
    }

}
