package com.sba.learning.controller;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sba.learning.entity.User;
import com.sba.learning.entity.Auth.AuthRequest;
import com.sba.learning.service.UserService;
import com.sba.learning.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public AuthController(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
	    this.userService = userService;
	    this.passwordEncoder = passwordEncoder;
	    this.jwtUtil = jwtUtil;
	}


//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
//        Authentication authentication = authManager.authenticate(
//            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//        );
//
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String token = jwtUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok(Map.of("token", token));
//    }

	@PostMapping("/login")
	public ResponseEntity<?> getToken(@RequestBody AuthRequest loginRequest) {
		User user = userService.findByEmail(loginRequest.getEmail());
		if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			String token = jwtUtil.generateToken(user.getEmail());
			return ResponseEntity.ok(Collections.singletonMap("token", token));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Collections.singletonMap("error", "Invalid credentials"));
		}
	}
}