package com.percy.inventory.Service;

import com.percy.inventory.Model.Mappers.VendorMapper;
import com.percy.inventory.Model.dto.Request.CreateVendorRequest;
import com.percy.inventory.Model.dto.Response.VendorResponse;
import com.percy.inventory.Model.entity.Vendor;
import com.percy.inventory.Repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorService {
    private final VendorRepository vendorRepository;

    public VendorResponse createVendor(CreateVendorRequest request){
        if (vendorRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Vendor with this email already exists");
        }

        if (vendorRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Vendor with this phone already exists");
        }

        Vendor vendor = VendorMapper.toEntity(request);
        Vendor savedVendor = vendorRepository.save(vendor);
        return VendorMapper.toResponse(savedVendor);
    }
}
