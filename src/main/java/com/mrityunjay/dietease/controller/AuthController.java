package com.mrityunjay.dietease.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.mrityunjay.dietease.dto.AuthResponse;
import com.mrityunjay.dietease.dto.LoginRequest;
import com.mrityunjay.dietease.entity.Users;
import com.mrityunjay.dietease.exception.ResourceNotFoundException;
import com.mrityunjay.dietease.repository.UsersRepository;
import com.mrityunjay.dietease.util.JwtUtil;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        
        // 1. Find the user by email
        Users user = usersRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this email"));

        // 2. The crucial check: Does the raw password match the hashed database password?
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            // If hacker guesses wrong, throw a 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, "Invalid Credentials"));
        }

        // 3. If password is correct, print the VIP Badge!
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return ResponseEntity.ok(new AuthResponse(token, "Login Successful!"));
    }
}