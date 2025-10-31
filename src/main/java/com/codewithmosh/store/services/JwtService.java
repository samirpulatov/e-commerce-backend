package com.codewithmosh.store.services;

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


    public String generateToken(String email) {
        final long tokenExpiration = 86400; // 1 day in seconds

        // Build and sign JWT token with email as subject
        return Jwts.builder()
                .subject(email)
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


    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

}

