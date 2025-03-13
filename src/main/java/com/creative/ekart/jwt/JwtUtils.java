package com.creative.ekart.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    @Value("${security.jwt.secret-key}")
    private String secretKey ;
    @Value("${security.jwt.expiration}")
    private Long expiration ;


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",userDetails.getAuthorities());
        return buildToken(userDetails, claims);
    }
    public Cookie generateTokenCookie(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",userDetails.getAuthorities());
        String token = buildToken(userDetails, claims);
        Cookie cookie = new Cookie("authToken", token);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        return cookie;
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
        return buildToken(userDetails, claims);
    }

    private String buildToken(UserDetails userDetails, Map<String, Object> claims) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey())
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }
    public String extractUsername(String token) {
        return parseToken(token).getSubject();
    }

    public boolean isExpired(String token) {
        Date expiration = parseToken(token).getExpiration();
        return expiration.before(new Date());
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private SecretKey secretKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Cookie generateEmptyCookie() {
        Cookie cookie = new Cookie("authToken", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }
}
