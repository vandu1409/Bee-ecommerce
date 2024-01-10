package com.beeecommerce.filter;

import com.beeecommerce.exception.core.ErrorDetails;
import com.beeecommerce.service.AccountService;
import com.beeecommerce.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AccountService userDetailsService;


    @Value("${app.cors-url}")
    private String corsUrl;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String jwtHeader = request.getHeader("Authorization");

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtHeader.substring(7);
        StringBuilder username = new StringBuilder();
        try {
            username.append(jwtService.extractUsername(token));
        } catch (ExpiredJwtException ex) {
            ErrorDetails errRes = handlerExpiredJwtException(ex, request);

            response.setStatus(errRes.getStatusCode());
            response.setContentType("application/json");
            response.setHeader("Access-Control-Allow-Origin", corsUrl);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getOutputStream(), errRes);

            return;
        }

        if (!username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username.toString());

            if (jwtService.isValidToken(token)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }


    public ErrorDetails handlerExpiredJwtException(ExpiredJwtException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return ErrorDetails
                .builder()
                .statusCode(status.value())
                .status(status.getReasonPhrase())
                .message("Token expired") // Don't change this message. It's used in frontend
                .timestamp(new Date())
                .exceptionClass(ex.getClass().getSimpleName())
                .path(request.getRequestURI())
                .build();

    }
}

