package com.example.cart_service.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
@Component
@Slf4j
public class UserContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        
        String userId = request.getHeader("X-User-Id");
        
        log.info("=== USER CONTEXT FILTER ===");
        log.info("Request URI: {}", request.getRequestURI());
        log.info("X-User-Id header: {}", userId);
        
        if (userId != null && !userId.isEmpty()) {
            UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("✅ Set SecurityContext for userId: {}", userId);
        } else {
            log.warn("❌ No X-User-Id header found");
        }
        
        try {
            filterChain.doFilter(request, response);
        } finally {
            // Chỉ clear sau khi request hoàn thành
            SecurityContextHolder.clearContext();
            log.debug("Cleared SecurityContext");
        }
    }
}
