package com.sba.learning.service;

import java.util.List;

import com.sba.learning.DTO.UserDTO;
import com.sba.learning.entity.User;

public interface UserService {
    UserDTO createUser(UserDTO dto);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO dto);
    void deleteUser(Long id);
    User findByEmail(String email);
}
