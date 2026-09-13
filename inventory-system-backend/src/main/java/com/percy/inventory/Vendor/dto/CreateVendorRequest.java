package com.percy.inventory.Vendor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateVendorRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Phone is required")
        String phone,

        @Email(message = "Email must be valid")
        String email,

        String address
) {}