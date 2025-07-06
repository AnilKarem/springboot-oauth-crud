package com.sba.learning.util;


import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String base64Secret;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(base64Secret);
        secretKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA512");
    }

    public String generateToken(String subject) {
        return Jwts.builder()
            .setSubject(subject)
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact();
    }
}
