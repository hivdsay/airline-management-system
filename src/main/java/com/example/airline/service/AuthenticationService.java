package com.example.airline.service;

import com.example.airline.dto.auth.AuthenticationRequest;
import com.example.airline.dto.auth.AuthenticationResponse;
import com.example.airline.dto.auth.RegisterRequest;
import com.example.airline.entity.User;
import com.example.airline.exception.user.UserAlreadyExistsException;
import com.example.airline.exception.user.UserNotFoundException;
import com.example.airline.mapper.UserMapper;
import com.example.airline.repository.UserRepository;
import com.example.airline.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // REGISTER
    public AuthenticationResponse register(RegisterRequest request) {

        // Email already exists?
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "Email already in use: " + request.getEmail());
        }

        // Convert DTO to User entity
        User user = userMapper.fromRegisterRequest(request);

        // Password encode
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Default role if empty
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of("ROLE_USER"));
        }

        User saved = userRepository.save(user);

        // Generate JWT token
        String token = jwtService.generateToken(saved.getEmail(), saved.getRoles());

        return new AuthenticationResponse(token);
    }

    // LOGIN
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        // Spring Security authentication
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Fetch the user
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + request.getEmail()));

        // Generate token
        String token = jwtService.generateToken(user.getEmail(), user.getRoles());

        return new AuthenticationResponse(token);
    }
}


