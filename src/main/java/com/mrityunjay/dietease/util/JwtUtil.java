package com.mrityunjay.dietease.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 1. The Master Key (In a real app, hide this in application.properties!)
    // Generating a secure 256-bit key for HMAC-SHA256
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // 2. Badge expiration time (e.g., 24 hours)
    private final long EXPIRATION_TIME = 86400000; 

    // 3. The Print Method
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email) // The user's identity
                .claim("role", role) // Add their permission level to the badge
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY) // Stamp it with the unforgeable cryptographic seal
                .compact();
    }

    private Claims extractAllClaims (String token){
        return Jwts.parserBuilder()
        .setSigningKey(SECRET_KEY)
        .build()
        .parseClaimsJws(token)
        .getBody();

    }

    public String extractEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token){
        return extractAllClaims(token).get("role",String.class);
    }

    public boolean isTokenValid(String token){
        try{
            extractAllClaims(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}