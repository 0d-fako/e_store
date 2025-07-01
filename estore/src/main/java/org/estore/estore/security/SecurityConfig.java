package org.estore.estore.security;

import org.estore.estore.security.filter.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestAttribute;

@Configuration
public class SecurityConfig {


    private final RequestAttribute requestAttribute;

    public SecurityConfig(RequestAttribute requestAttribute) {
        this.requestAttribute = requestAttribute;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterAt(new AuthenticationFilter(),
                        BasicAuthenticationFilter.class)
                .build();

    }
}
