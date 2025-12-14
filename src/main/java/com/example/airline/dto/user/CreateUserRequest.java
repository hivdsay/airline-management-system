package com.example.airline.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class CreateUserRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    // Kullanıcı roles göndermezse ROLE_USER olarak açılır
    private Set<String> roles = Set.of("ROLE_USER");
}
