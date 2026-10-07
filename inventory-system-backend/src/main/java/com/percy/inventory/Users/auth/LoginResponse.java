package com.percy.inventory.Users.auth;

import com.percy.inventory.Users.Role;

public record LoginResponse(
        String token,
        String username,
        AccountType accountType,
        Role role,
        Long warehouseId,
        String warehouseName
) {
}