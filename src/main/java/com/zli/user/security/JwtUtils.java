package com.zli.user.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {


    @Value("${jwt.secret}")
    private String secret;

    private final long expirationMs = 86400000; // 24 hours

    public String generateJwtToken(String username, String role, Long id) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("id", id)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getRoleFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }

    public boolean admin (String header) {
        //String token = header.split(" ")[1].trim();
        if (getRoleFromJwtToken(header).equals("ADMIN")) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean user (String header) {
        //String token = header.split(" ")[1].trim();
        if (getRoleFromJwtToken(header).equals("USER")) {
            return true;
        }
        else {
            return false;
        }
    }

    public String token(String header) {
        return header.split(" ")[1].trim();
    }

    public Long getIdFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("id", Long.class);
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // Invalid token
            return false;
        }
    }
}