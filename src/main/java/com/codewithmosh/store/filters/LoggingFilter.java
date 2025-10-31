package com.codewithmosh.store.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Log incoming request URI
        System.out.println("Request URI: " + request.getRequestURI());

        // Continue with the next filter or controller
        filterChain.doFilter(request, response);

        // Log response status after processing
        System.out.println("Response: " + response.getStatus());
    }
}

