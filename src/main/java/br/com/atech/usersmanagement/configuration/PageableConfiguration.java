package br.com.atech.usersmanagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;

public class PageableConfiguration {
    @Bean
    public PageableHandlerMethodArgumentResolver pageableResolver() {
        return new PageableHandlerMethodArgumentResolver();
    }
}
