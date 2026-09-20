package com.percy.inventory.Users.dto;

import com.percy.inventory.Users.AccountType;
import com.percy.inventory.Users.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

        @NotBlank(message = "Username is required")
        @Size(max = 50)
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100)
        String password,

        @NotBlank(message = "Full name is required")
        @Size(max = 100)
        String fullName,

        @Email(message = "Invalid email format")
        @Size(max = 100)
        String email,

        @NotNull(message = "Account type is required")
        AccountType accountType,

        Role role

) {
}