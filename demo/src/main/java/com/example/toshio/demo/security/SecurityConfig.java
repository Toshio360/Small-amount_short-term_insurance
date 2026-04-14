package com.example.toshio.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/contract/**").authenticated()
                .anyRequest().authenticated()
        ).oauth2Login((oauth2) -> oauth2
                .loginPage("/oauth2/authorization/keycloak")
        ).logout((logout) -> logout
                .logoutSuccessUrl("/").permitAll()
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}