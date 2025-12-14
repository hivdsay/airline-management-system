package com.example.airline.mapper;

import com.example.airline.dto.auth.RegisterRequest;
import com.example.airline.dto.user.CreateUserRequest;
import com.example.airline.dto.user.UpdateUserRequest;
import com.example.airline.dto.user.UserResponse;
import com.example.airline.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // CREATE: DTO → Entity
    public User toEntity(CreateUserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());

        // ⚠️ Password encode edilmez — serviste encode edilecek
        user.setPassword(request.getPassword());

        // Rolleri direkt set ediyoruz (serviste validate ederiz)
        user.setRoles(request.getRoles());

        return user;
    }

    // RegisterRequest için toEntity  ← BUNU EKLEDİK
    public User toEntity(RegisterRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRoles(request.getRoles());
        return user;
    }
    public User fromRegisterRequest(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // encoding service içinde yapılacak
        return user;
    }



    // UPDATE: Entity üzerinde alan bazlı güncelleme
    public void updateEntity(User user, UpdateUserRequest request) {

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }

        // ⚠️ Password yine serviste encode edilecek
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(request.getPassword());
        }

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            user.setRoles(request.getRoles());
        }
    }

    // ENTITY → DTO (şifre gönderilmez)
    public UserResponse toResponse(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles());
        return dto;
    }
}
