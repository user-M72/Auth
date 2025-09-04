package com.example.Auth2.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    private SecretKey key;

    private final Key secretKey = Keys.hmacShaKeyFor("superSecretKey123superSecretKey123".getBytes());

    @PostConstruct
    public void init(){
        this.key= Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new  Date (( new  Date ()).getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder().
                setSigningKey(key).build().parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

    public boolean validateJwtToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return  true ;
        } catch (SecurityException e) {
            System.out.println( "Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println( "Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println( "JWT token expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println( "JWT token is not supported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println( "JWT claims string is empty: " + e.getMessage());
        }
        return  false ;
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}

