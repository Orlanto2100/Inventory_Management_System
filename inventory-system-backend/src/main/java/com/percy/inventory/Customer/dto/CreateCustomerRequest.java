package com.percy.inventory.Customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(
        @NotBlank String name,
        @NotBlank String phone,
        @Email String email,
        String address
) {}
