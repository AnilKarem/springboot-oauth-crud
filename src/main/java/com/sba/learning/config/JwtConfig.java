//package com.sba.learning.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.sba.learning.util.JwtUtil;
//
//@Configuration
//public class JwtConfig {
//
//    @Value("${app.jwt.secret}")
//    private String base64Secret;
//
//    @Bean
//    public JwtUtil jwtUtil() {
//        return new JwtUtil(base64Secret);
//    }
//}