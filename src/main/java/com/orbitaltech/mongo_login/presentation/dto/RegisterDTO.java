package com.orbitaltech.mongo_login.presentation.dto;

import com.orbitaltech.mongo_login.integration.models.embedded.UserRole;

public record RegisterDTO(String username,
                          String password,
                          String email,
                          UserRole role) {
}
