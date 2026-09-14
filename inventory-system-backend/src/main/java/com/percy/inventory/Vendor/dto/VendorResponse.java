package com.percy.inventory.Vendor.dto;

import com.percy.inventory.Vendor.VendorStatus;

public record VendorResponse(
        Long id,
        String name,
        String phone,
        String email,
        String address,
        VendorStatus status
) {}