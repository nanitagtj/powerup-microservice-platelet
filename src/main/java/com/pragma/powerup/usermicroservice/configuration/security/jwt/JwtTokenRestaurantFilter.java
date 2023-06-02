package com.pragma.powerup.usermicroservice.configuration.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtTokenRestaurantFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;
    private List<String> excludedPrefixes = Arrays.asList("/auth/**", "/swagger-ui/**", "/v3/api-docs/**");

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (shouldNotFilter(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = getToken(request);
            if (token != null && jwtProvider.validateToken(token)) {
                List<String> roles = jwtProvider.getRoleFromToken(token);
                if (isCreateRestaurantRequest(request) && !roles.contains("ROLE_ADMIN")) {
                    throw new AuthenticationException("Unauthorized");
                }

                if (isCreateDishRequest(request) && !roles.contains("ROLE_OWNER")){
                    throw new AuthenticationException("Unauthorized");
                }

                if (isUpdateDishRequest(request) && !roles.contains("ROLE_OWNER")){
                    throw new AuthenticationException("Unauthorized");
                }

                if (isUpdateStatusDishRequest(request) && !roles.contains("ROLE_OWNER")){
                    throw new AuthenticationException("Unauthorized");
                }

                filterChain.doFilter(request, response);
                return;
            }

            throw new AuthenticationException("Unauthorized");
        } catch (AuthenticationException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String currentRoute = request.getServletPath();
        for (String prefix : excludedPrefixes) {
            if (pathMatcher.matchStart(prefix, currentRoute)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCreateRestaurantRequest(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("POST")
                && request.getRequestURI().contains("/platelet/restaurant");
    }

    private boolean isCreateDishRequest(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("POST")
                && request.getRequestURI().contains("/platelet/dish");
    }

    private boolean isUpdateDishRequest(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("PATCH")
                && request.getRequestURI().contains("/dish/{id}");
    }

    private boolean isUpdateStatusDishRequest(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("PUT")
                && request.getRequestURI().contains("/platelet/{id}/status");
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
