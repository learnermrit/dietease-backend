package com.mrityunjay.dietease.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrityunjay.dietease.dto.CreateUserRequest;
import com.mrityunjay.dietease.dto.UpdateUserRequest;
import com.mrityunjay.dietease.dto.UserDTO;
import com.mrityunjay.dietease.entity.Users;
import com.mrityunjay.dietease.exception.ResourceNotFoundException;
import com.mrityunjay.dietease.exception.UserAlreadyExistsException;
import com.mrityunjay.dietease.repository.UsersRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UsersRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public UserDTO getUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertToDTO(user);
    }
    
 @Override
public UserDTO getUserByEmail(String email) {
    // 1. Try to open the Optional box.
    // 2. If it's empty, instantly throw the exception.
    // 3. If it has a user, assign it to the 'user' variable.
    Users user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
            
    return convertToDTO(user);
}
    @Override
    public UserDTO getUserByPhone(String phone) {
        Users user = userRepository.findByPhone(phone);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with phone: " + phone);
        }
        return convertToDTO(user);
    }
    
    @Override
    public UserDTO createUser(CreateUserRequest request) {
        // Check if user already exists by email
     if (userRepository.findByEmail(request.getEmail()).isPresent()) {
        throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
    }

       
        
        Users user = new Users();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPasswordHash(hashedPassword);
       // user.setPasswordHash(request.getPassword()); // TODO: use bcrypt hashing
        user.setRole(request.getRole());
        user.setStatus(request.getStatus());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        Users savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }
    
    @Override
    public UserDTO updateUser(Long id, UpdateUserRequest request) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }
        if (request.getStatus() != null) {
            user.setStatus(request.getStatus());
        }
        user.setUpdatedAt(LocalDateTime.now());
        
        Users updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }
    
    @Override
    public void deleteUser(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }
    
    private UserDTO convertToDTO(Users user) {
        return new UserDTO(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
