package com.example.airline.service;

import com.example.airline.dto.user.CreateUserRequest;
import com.example.airline.dto.user.UpdateUserRequest;
import com.example.airline.dto.user.UserResponse;
import com.example.airline.entity.User;
import com.example.airline.exception.user.EmailAlreadyExistsException;
import com.example.airline.exception.user.UserNotFoundException;
import com.example.airline.mapper.UserMapper;
import com.example.airline.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserResponse createUser(CreateUserRequest request) {

        // Aynı email varsa hata
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = userMapper.toEntity(request);

        // Şifre encode edilmeli
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saved = userRepository.save(user);

        return userMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Eğer email değişiyorsa tekrar duplicate check
        if (request.getEmail() != null &&
                !request.getEmail().equals(user.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {

            throw new EmailAlreadyExistsException(request.getEmail());
        }

        // Mapper ile değişiklikleri entitiy’e uygula
        userMapper.updateEntity(user, request);

        // Şifre değişiyorsa encode edilmeli
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User saved = userRepository.save(user);

        return userMapper.toResponse(saved);
    }


    @Transactional
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
    }

    // USERDETAILS FOR SECURITY
    @Transactional(readOnly = true)
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Email: " + email));
    }
}
