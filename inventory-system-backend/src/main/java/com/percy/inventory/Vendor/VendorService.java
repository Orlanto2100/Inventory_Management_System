package com.percy.inventory.Vendor;

import com.percy.inventory.Vendor.dto.CreateVendorRequest;
import com.percy.inventory.Vendor.dto.UpdateVendorRequest;
import com.percy.inventory.Vendor.dto.VendorResponse;
import com.percy.inventory.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorResponse createVendor(CreateVendorRequest request) {
        Vendor vendor = vendorMapper.toEntity(request);
        Vendor savedVendor = vendorRepository.save(vendor);
        return vendorMapper.toResponse(savedVendor);
    }

    public VendorResponse getVendorById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        return vendorMapper.toResponse(vendor);
    }

    public List<VendorResponse> listVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendorMapper::toResponse)
                .toList();
    }

    public VendorResponse updateVendor(Long vendorId, UpdateVendorRequest request) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        vendorMapper.updateEntity(vendor, request);

        Vendor savedVendor = vendorRepository.save(vendor);
        return vendorMapper.toResponse(savedVendor);
    }

    public void deleteVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        vendorRepository.delete(vendor);
    }
}
