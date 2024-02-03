package com.mybackyard.backend.config.security;

import com.mybackyard.backend.service.interfaces.ApiKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class APIKeyFilter extends OncePerRequestFilter {

    private final ApiKeyService apiKeyService;

    public APIKeyFilter(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().matches("/api/(.*)")) {
            String requestAPIKey = request.getHeader("X-API-KEY");
            long associatedUser = apiKeyService.matchKeyToUserId(requestAPIKey);
            if (associatedUser != 0) {
                if (associatedUser == 1) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(requestAPIKey, null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                else {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(requestAPIKey, null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
            }
        }
        filterChain.doFilter(request, response);
    }
}
