package com.percy.inventory.Vendor;

import com.percy.inventory.Vendor.dto.CreateVendorRequest;
import com.percy.inventory.Vendor.dto.UpdateVendorRequest;
import com.percy.inventory.Vendor.dto.VendorResponse;

public class VendorMapper {
    public static VendorResponse toResponse(Vendor vendor){
        return new VendorResponse(
                vendor.getVendorId(),
                vendor.getName(),
                vendor.getPhone(),
                vendor.getEmail(),
                vendor.getAddress()
        );
    }

    public static Vendor toEntity(CreateVendorRequest request) {
        Vendor vendor = new Vendor();

        vendor.setName(request.getName());
        vendor.setPhone(request.getPhone());
        vendor.setEmail(request.getEmail());
        vendor.setAddress(request.getAddress());

        return vendor;
    }

    public static void updateEntity(Vendor vendor, UpdateVendorRequest request) {
        if (request.getName() != null) {
            vendor.setName(request.getName());
        }

        if (request.getPhone() != null) {
            vendor.setPhone(request.getPhone());
        }

        if (request.getEmail() != null) {
            vendor.setEmail(request.getEmail());
        }

        if (request.getAddress() != null) {
            vendor.setAddress(request.getAddress());
        }
    }
}
