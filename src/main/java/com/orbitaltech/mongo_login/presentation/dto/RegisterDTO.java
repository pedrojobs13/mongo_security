package com.orbitaltech.mongo_login.presentation.dto;

public record RegisterDTO(String username,
                          String password,
                          String email,
                          String role) {
}
