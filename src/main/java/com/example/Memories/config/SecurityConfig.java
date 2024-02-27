package com.example.Memories.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public DefaultSecurityFilterChain webSecurityConfiguration(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests().anyRequest().permitAll();
        return httpSecurity.build();
    }
}
