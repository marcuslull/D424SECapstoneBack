package com.mybackyard.backend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final APIKeyFilter apiKeyFilter;

    public SecurityConfiguration(APIKeyFilter apiKeyFilter) {
        this.apiKeyFilter = apiKeyFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                // TODO: Figure out how to configure CSRF for POST and PATCH methods - v.N
                .csrf(csrf -> csrf.disable()) // needed for posts and H2-console
                //.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()).disable()) // uncomment to enable h2-console
                .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                                //.requestMatchers("/h2-console/*").permitAll() // uncomment to enable h2-console
                                .anyRequest().authenticated()
                                );
        return httpSecurity.build();
    }
}
