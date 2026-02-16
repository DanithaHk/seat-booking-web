package com.example.backend.controller;

import com.example.backend.dto.UserDTO;
import com.example.backend.service.UserService;
import com.example.backend.util.JwtUtil;
import com.example.backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for authentication with JWT token generation
 */
@RestController
@RequestMapping("/api/auth")
public class AuthControllerJWT {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Register a new user
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = userService.saveUser(userDTO);
            
            if (result == VarList.Created) {
                UserDTO savedUser = userService.findByEmail(userDTO.getEmail());
                
                // Generate JWT token for the newly registered user
                UserDTO dto = new UserDTO();
                dto.setEmail(savedUser.getEmail());
                dto.setRole(savedUser.getRole());
                String token = jwtUtil.generateToken(dto);

                
                response.put("success", true);
                response.put("message", "User registered successfully");
                response.put("token", token);
                response.put("data", savedUser);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else if (result == VarList.Not_Acceptable) {
                response.put("success", false);
                response.put("message", "Email already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            } else {
                response.put("success", false);
                response.put("message", "Registration failed");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Login with JWT token generation
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");

            // Authenticate user
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );

            // Load user details
            final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            final UserDTO user = userService.findByEmail(email);

            // Generate JWT token
            final String token = jwtUtil.generateToken(user);
            System.out.println("role: " + user.getRole().name());
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("token", token);
            response.put("data", user);
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            response.put("success", false);
            response.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    
}