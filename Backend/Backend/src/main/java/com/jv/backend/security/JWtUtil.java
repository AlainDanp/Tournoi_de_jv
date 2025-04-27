package com.jv.backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.security.Key;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWtUtil {

    private static final Key keys = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final static long expirationTime = 86400000; // 1 day in milliseconds
    static Key key=Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static String generateToken(String username) {
        String token=Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(key).compact();
        return token;
    }
    public static boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    public static boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody().getExpiration().before(new Date(expirationTime));
    }

    public static String getUserName(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
