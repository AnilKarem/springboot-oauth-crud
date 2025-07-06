package com.sba.learning.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.sba.learning.DTO.UserDTO;
import com.sba.learning.entity.User;
import com.sba.learning.events.UserCreatedEvent;
import com.sba.learning.repository.UserRepository;
import com.sba.learning.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher eventPublisher;

    public UserServiceImpl(UserRepository userRepository, RestTemplate restTemplate, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public UserDTO createUser(UserDTO dto) {
        User user = new User(dto.getName(), dto.getEmail());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        user = userRepository.save(user);

        // Simulate dependent microservice call
        String response = restTemplate.getForObject("http://example-service/api/verify", String.class);
        if (!"ok".equalsIgnoreCase(response)) {
            throw new RuntimeException("Downstream service failed");
        }

        eventPublisher.publishEvent(new UserCreatedEvent(this, user.getId()));
        return mapToDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id).map(this::mapToDTO).orElseThrow();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return mapToDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
    
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
