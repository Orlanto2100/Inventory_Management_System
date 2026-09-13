package com.percy.inventory.Vendor.dto;

public record VendorResponse(
        Long id,
        String name,
        String phone,
        String email,
        String address
) {}
