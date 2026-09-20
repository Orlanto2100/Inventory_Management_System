package com.percy.inventory.Users.dto;

import com.percy.inventory.Users.AccountType;
import com.percy.inventory.Users.Role;

public record UserResponse(
        Long id,
        String username,
        String fullName,
        String email,
        AccountType accountType,
        Role role
) {
}