package com.percy.inventory.Users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserProfileRequest(

        @Size(max = 50, message = "Username must not exceed 50 characters")
        String username,

        @Size(max = 100, message = "Full name must not exceed 100 characters")
        String fullName,

        @Email(message = "Invalid email format")
        @Size(max = 100, message = "Email must not exceed 100 characters")
        String email

) {
}