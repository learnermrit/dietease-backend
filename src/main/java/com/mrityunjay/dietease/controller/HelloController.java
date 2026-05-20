package com.mrityunjay.dietease.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mrityunjay.dietease.dto.CreateUserRequest;
import com.mrityunjay.dietease.dto.UpdateUserRequest;
import com.mrityunjay.dietease.dto.UserDTO;
import com.mrityunjay.dietease.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HelloController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World from Spring Boot Backend";
    }
    
    // GET all users
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    // GET user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    // GET user by email
    @GetMapping("/users/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
    
    // GET user by phone
    @GetMapping("/users/phone/{phone}")
    public ResponseEntity<UserDTO> getUserByPhone(@PathVariable String phone) {
        UserDTO user = userService.getUserByPhone(phone);
        return ResponseEntity.ok(user);
    }
    
    // POST - Create new user
    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDTO createdUser = userService.createUser(request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User created successfully");
        response.put("data", createdUser);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    // PUT - Update user
    @PutMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        UserDTO updatedUser = userService.updateUser(id, request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User updated successfully");
        response.put("data", updatedUser);
        
        return ResponseEntity.ok(response);
    }
    
    // DELETE - Delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted successfully");
        
        return ResponseEntity.ok(response);
    }
}