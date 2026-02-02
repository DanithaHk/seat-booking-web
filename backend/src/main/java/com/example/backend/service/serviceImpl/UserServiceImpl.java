package com.example.backend.service.serviceImpl;

import com.example.backend.dto.UserDTO;
import com.example.backend.entity.Role;
import com.example.backend.entity.User;
import com.example.backend.repo.UserRepository;
import com.example.backend.service.UserService;
import com.example.backend.utill.VarList;

import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ================= Spring Security =================
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthority(user)
        );
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())); // ✅ correct
        return authorities;
    }


    // ================= Custom Methods =================
    @Override
    public UserDTO loadUserDetailsByUsername(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO searchUser(String email) {
        if (!userRepository.existsByEmail(email)) {
            return null;
        }
        User user = userRepository.findByEmail(email);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id.intValue())) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id.intValue());
    }

    @Override
    public void updateUserRole(String email, String newRole) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        user.setRole(Role.valueOf(newRole.toUpperCase()));
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAll() {
        return modelMapper.map(
                userRepository.findAll(),
                new TypeToken<List<UserDTO>>() {}.getType()
        );
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public int saveUser(UserDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));

        if (userDTO.getRole() == null) {
            userDTO.setRole(Role.USER);
        }

        userRepository.save(modelMapper.map(userDTO, User.class));
        return VarList.Created;
    }
}
