package com.sba.learning;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sba.learning.entity.User;
import com.sba.learning.repository.UserRepository;

@SpringBootApplication
public class LearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(UserRepository repo, PasswordEncoder passwordEncoder) {
	    return args -> {
	        if (repo.findByEmail("alice@example.com").isEmpty()) {
	            repo.save(new User("springbootoauthuser", "springbootuser@example.com", passwordEncoder.encode("password123"), "USER"));
	        }
	        if (repo.findByEmail("bob@example.com").isEmpty()) {
	            repo.save(new User("springbootoauthuser2", "springbootuser2@example.com", passwordEncoder.encode("password1234"), "USER"));
	        }
	    };
	}

}
