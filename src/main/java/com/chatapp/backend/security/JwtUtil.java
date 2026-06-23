package com.chatapp.backend.security;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "ThisIsMySuperSecretJwtKeyForChatAppProject2026SpringBootJava"
                    .getBytes());

    public String generateToken(
            String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 86400000))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String extractEmail(
            String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(
                        SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(
            String token,
            String email) {

        return extractEmail(token)
                .equals(email);
    }
}