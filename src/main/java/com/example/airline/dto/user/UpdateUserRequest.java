package com.example.airline.dto.user;

import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserRequest {

    private String email;
    private String password;
    private Set<String> roles;
}
