package com.percy.inventory.Vendor;

import com.percy.inventory.Vendor.dto.CreateVendorRequest;
import com.percy.inventory.Vendor.dto.UpdateVendorRequest;
import com.percy.inventory.Vendor.dto.VendorResponse;
import org.springframework.stereotype.Component;

@Component
public class VendorMapper {

    public VendorResponse toResponse(Vendor vendor) {
        return new VendorResponse(
                vendor.getId(),
                vendor.getName(),
                vendor.getPhone(),
                vendor.getEmail(),
                vendor.getAddress(),
                vendor.getStatus()
        );
    }

    public Vendor toEntity(CreateVendorRequest request) {
        return new Vendor(
                request.name(),
                request.phone(),
                request.email(),
                request.address()
        );
    }

    public void updateEntity(
            Vendor vendor,
            UpdateVendorRequest request) {

        vendor.update(
                request.name(),
                request.phone(),
                request.email(),
                request.address()
        );
    }
}