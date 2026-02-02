package com.example.backend.controller;

import com.example.backend.dto.AuthDTO;
import com.example.backend.dto.AuthRequestDTO;
import com.example.backend.dto.ResponseDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.service.UserService;
import com.example.backend.utill.JwtUtil;
import com.example.backend.utill.VarList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager,
            UserService userService
    ) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    // ================= LOGIN =================
    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO> authenticate(@RequestBody AuthRequestDTO authRequest) {

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(),
                    authRequest.getPassword()
                )
            );
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(
                            VarList.Unauthorized,
                            "Invalid email or password",
                            null
                    ));
        }

        UserDTO user = userService.loadUserDetailsByUsername(authRequest.getEmail());

        String token = jwtUtil.generateToken(user);

        AuthDTO authDTO = new AuthDTO(user.getEmail(), token, user.getRole());

        return ResponseEntity.ok(new ResponseDTO(
                VarList.OK,
                "Login successful",
                authDTO
        ));
    }
}