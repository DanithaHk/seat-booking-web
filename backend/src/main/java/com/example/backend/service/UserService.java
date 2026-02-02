package com.example.backend.service;

import com.example.backend.dto.UserDTO;
import java.util.List;

public interface UserService {

    UserDTO loadUserDetailsByUsername(String email);

    UserDTO searchUser(String email);

    void deleteUser(Long id);

    void updateUserRole(String email, String newRole);

    List<UserDTO> getAll();

    UserDTO findByEmail(String email);

    int saveUser(UserDTO userDTO);
}
