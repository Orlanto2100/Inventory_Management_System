package com.percy.inventory.Vendor;

import com.percy.inventory.Vendor.dto.CreateVendorRequest;
import com.percy.inventory.Vendor.dto.UpdateVendorRequest;
import com.percy.inventory.Vendor.dto.VendorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {
    private final VendorRepository vendorRepository;

    public VendorResponse createVendor(CreateVendorRequest request) {
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

    public VendorResponse getVendorById(Long id){
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor with this id does not exist"));
        return VendorMapper.toResponse(vendor);
    }

    public VendorResponse getVendorByName(String name){
        Vendor vendor = vendorRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Vendor with this name does not exist"));
        return VendorMapper.toResponse(vendor);
    }

    public List<VendorResponse> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(VendorMapper::toResponse)
                .toList();
    }

    public VendorResponse updateVendorById(
            Long id,
            UpdateVendorRequest request) {

        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found"));

        VendorMapper.updateEntity(vendor, request);

        Vendor updatedVendor = vendorRepository.save(vendor);

        return VendorMapper.toResponse(updatedVendor);
    }

    public void deleteVendorById(Long id){
        if  (!vendorRepository.existsById(id)) {
            throw new RuntimeException("Vendor with this id does not exist");
        }
        vendorRepository.deleteById(id);
    }
}
