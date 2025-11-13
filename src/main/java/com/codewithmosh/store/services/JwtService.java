package com.codewithmosh.store.services;

import com.codewithmosh.store.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secret;


    public String generateAccessToken(User user) {
        final long tokenExpiration = 300; // 5 minutes

        return generateAccessToken(user, tokenExpiration);
    }

    public String generateRefreshToken(User user) {
        final long tokenExpiration = 604800; // 7 days

        return generateAccessToken(user, tokenExpiration);
    }

    private String generateAccessToken(User user, long tokenExpiration) {
        // Build and sign JWT token with userId as subject
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }


    public boolean validateToken(String token) {
        try {

            var claims = getClaims(token);
            // Return true if token is not expired
            return claims.getExpiration().after(new Date());
        } catch (JwtException e) {
            // Return false if token is invalid or expired
            return false;
        }
    }

    private Claims getClaims(String token) {
        // Parse and verify JWT signature using the secret key
        // extract claims
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes())) // verify signature
                .build()
                .parseSignedClaims(token) // parse and validate token
                .getPayload();
    }


    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }

}

