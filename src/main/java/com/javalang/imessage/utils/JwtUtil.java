package com.javalang.imessage.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private static String secret;
    private static final long EXPIRATION_TIME_DAY = 86400000; // 1天
    private static final long EXPIRATION_TIME_MONTH = EXPIRATION_TIME_DAY * 30; // 1月
    private static final long EXPIRATION_TIME_YEAR = EXPIRATION_TIME_MONTH * 12; // 1年

    @Value("${jwt.token.secret}")
    private void setSecret(String secret) {
        JwtUtil.secret = secret;
    }

    private static Key getSigningKey() {
        // System.out.println("secret:" + secret);
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_YEAR))
                .signWith(getSigningKey())
                .compact();
    }

    public static String getUsernameFromToken(String token) {
        if (token.isEmpty())
            return null;
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            // Log the exception and return null or handle it as needed
            log.warn("Invalid JWT token: " + e.getMessage());
            return null;
        }
    }
}

