package com.example.backend.dto;

import com.example.backend.entity.Role;

public class AuthDTO {

    private String email;
    private String token;
    private Role role;

    // 🔹 No-args constructor
    public AuthDTO() {}

    // 🔹 REQUIRED constructor (your error fix)
    public AuthDTO(String email, String token, Role role) {
        this.email = email;
        this.token = token;
        this.role = role;
    }

    // 🔹 getters & setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
