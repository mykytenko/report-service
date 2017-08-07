package com.tests.reportservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;


@Configuration
public class SpringConfig {

    /**
     * Is needed for Thymeleaf "extras" integration module for Spring Security
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}
