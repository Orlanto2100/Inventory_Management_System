package com.percy.inventory.Users.dto;

import com.percy.inventory.Users.AccountType;
import com.percy.inventory.Users.Role;

public record LoginResponse(
        String token,
        String username,
        AccountType accountType,
        Role role
) {
}