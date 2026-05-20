package com.mrityunjay.dietease.service;

import java.util.List;

import com.mrityunjay.dietease.dto.CreateUserRequest;
import com.mrityunjay.dietease.dto.UpdateUserRequest;
import com.mrityunjay.dietease.dto.UserDTO;

public interface UserService {
    
    List<UserDTO> getAllUsers();
    
    UserDTO getUserById(Long id);
    
    UserDTO getUserByEmail(String email);
    
    UserDTO getUserByPhone(String phone);
    
    UserDTO createUser(CreateUserRequest request);
    
    UserDTO updateUser(Long id, UpdateUserRequest request);
    
    void deleteUser(Long id);
}
