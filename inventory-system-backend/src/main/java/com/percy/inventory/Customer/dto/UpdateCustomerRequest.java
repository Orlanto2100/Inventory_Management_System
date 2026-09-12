package com.percy.inventory.Customer.dto;

import jakarta.validation.constraints.Email;

public record UpdateCustomerRequest(
        String name,
        String phone,
        @Email(message = "Email must be valid")
        String email,
        String address
) {}