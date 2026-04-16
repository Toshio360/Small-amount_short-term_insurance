package com.example.toshio.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private CustomOidcUserService customOAuth2UserService;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/contract/**").authenticated()
                .anyRequest().authenticated()).oauth2Login((oauth2) -> oauth2
                        .loginPage("/oauth2/authorization/keycloak")
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(customOAuth2UserService)))
                .logout((logout) -> logout
                        .logoutSuccessHandler((request, response, authentication) -> {
                            String logoutUrl = "http://localhost:8080/realms/my-realm/protocol/openid-connect/logout?redirect_uri=http://localhost:8089/";
                            response.sendRedirect(logoutUrl);
                        }));

        return http.build();
    }
}