package com.example.Memories.config;

import com.example.Memories.security.CustomOidcUserService;
import com.example.Memories.security.OAuth2ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final OAuth2ClientService oAuth2ClientService;

    public SecurityConfig(OAuth2ClientService oAuth2ClientService) {
        this.oAuth2ClientService = oAuth2ClientService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(Customizer.withDefaults())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request.requestMatchers("login", "console").permitAll().anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/login_callback", true))
                .oauth2Login(form -> form.defaultSuccessUrl("/login_callback", true)
                        .userInfoEndpoint(i -> i.oidcUserService(this.oAuth2ClientService())));
        return httpSecurity.build();
    }

    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oAuth2ClientService() {
        return new CustomOidcUserService(oAuth2ClientService);
    }
}
