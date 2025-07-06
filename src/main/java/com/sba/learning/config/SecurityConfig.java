package com.sba.learning.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import io.jsonwebtoken.io.Decoders;
import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/auth/login").permitAll()     // Allow login endpoint to everyone
	            .requestMatchers("/api/users/**").authenticated()
	            .anyRequest().permitAll()
	        )
	        .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
	        .csrf(csrf -> csrf.disable()); // Optional, disable CSRF for testing APIs like Postman

	    return http.build();
	}

    @Bean
    public JwtDecoder jwtDecoder(@Value("${app.jwt.secret}") String base64Secret) {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        SecretKey secretKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA512");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    
}
