package com.percy.inventory.Customer.dto;

public record CustomerResponse(
        Long id,
        String name,
        String phone,
        String email,
        String address
) {
}