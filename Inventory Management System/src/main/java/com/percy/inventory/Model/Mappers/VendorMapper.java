package com.percy.inventory.Model.Mappers;

import com.percy.inventory.Model.dto.Request.CreateVendorRequest;
import com.percy.inventory.Model.dto.Response.VendorResponse;
import com.percy.inventory.Model.entity.Vendor;

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
}
