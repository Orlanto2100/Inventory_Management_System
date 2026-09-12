package com.percy.inventory.Customer.dto;

public record UpdateCustomerRequest(
        String name,
        String phone,
        String email,
        String address
) {
}