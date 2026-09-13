package com.percy.inventory.Vendor.dto;

public record UpdateVendorRequest(
        String name,
        String phone,
        String email,
        String address
) {}
