package com.creative.ekart.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtCookieFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtCookieFilter(JwtUtils jwtUtils,@Qualifier("custom") UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String jwtToken = getTokenFromCookie(cookies);
        try{
        if (jwtToken != null ) {
            String username = jwtUtils.extractUsername(jwtToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtUtils.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);

            }

        }
        }catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);


    }

    private String getTokenFromCookie(Cookie[] cookies) {

        if (cookies == null) { return null; }
        String authToken = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("authToken")) {
                 authToken = cookie.getValue();

                 if(authToken != null) {
                     authToken = authToken.replace("Bearer ", "");
                 }
                 break;
            }
        }
        return authToken;
    }
}
