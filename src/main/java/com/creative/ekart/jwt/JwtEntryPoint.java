package com.creative.ekart.jwt;

import com.creative.ekart.payload.ErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ErrorDetails exception = ErrorDetails.builder()
                .withMessage(authException.getMessage())
                .withPath(request.getRequestURI())
                .withStatus(HttpStatus.UNAUTHORIZED)
                .build();
//        AuthException exception = new AuthException(authException.getMessage(),request.getRequestURI(), HttpStatus.FORBIDDEN);
        ObjectMapper mapper = new ObjectMapper();

        response.getWriter().write(mapper.writeValueAsString(exception));

    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        ErrorDetails exception = ErrorDetails.builder()
                .withMessage(accessDeniedException.getMessage())
                .withPath(request.getRequestURI())
                .withStatus(HttpStatus.FORBIDDEN)
                .build();
//        AuthException exception = new AuthException(authException.getMessage(),request.getRequestURI(), HttpStatus.FORBIDDEN);
        ObjectMapper mapper = new ObjectMapper();

        response.getWriter().write(mapper.writeValueAsString(exception));
    }
}
