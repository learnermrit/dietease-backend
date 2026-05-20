package com.mrityunjay.dietease.dto;

public class UpdateUserRequest {
    
    private String fullName;
    private String phone;
    private String role;
    private String status;

    // Constructors
    public UpdateUserRequest() {}

    public UpdateUserRequest(String fullName, String phone, String role, String status) {
        this.fullName = fullName;
        this.phone = phone;
        this.role = role;
        this.status = status;
    }

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
