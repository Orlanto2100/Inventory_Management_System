package com.percy.inventory.Vendor;

import com.percy.inventory.Vendor.dto.CreateVendorRequest;
import com.percy.inventory.Vendor.dto.UpdateVendorRequest;
import com.percy.inventory.Vendor.dto.VendorResponse;
import com.percy.inventory.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vendor not found"));

        return vendorMapper.toResponse(vendor);
    }

    public Page<VendorResponse> listVendors(
            String search,
            VendorStatus status,
            Pageable pageable) {

        Specification<Vendor> specification = Specification.allOf();

        if (search != null && !search.isBlank()) {
            String searchTerm = search.trim().toLowerCase();

            specification = specification.and((root, query, cb) ->
                    cb.or(
                            cb.like(
                                    cb.lower(root.get("name")),
                                    "%" + searchTerm + "%"
                            ),
                            cb.like(
                                    cb.lower(root.get("email")),
                                    "%" + searchTerm + "%"
                            ),
                            cb.like(
                                    root.get("phone"),
                                    "%" + searchTerm + "%"
                            )
                    )
            );
        }

        if (status != null) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("status"), status)
            );
        }

        return vendorRepository
                .findAll(specification, pageable)
                .map(vendorMapper::toResponse);
    }

    public VendorResponse updateVendor(
            Long vendorId,
            UpdateVendorRequest request) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vendor not found"));

        vendorMapper.updateEntity(vendor, request);

        Vendor savedVendor = vendorRepository.save(vendor);

        return vendorMapper.toResponse(savedVendor);
    }

    public void deactivateVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vendor not found"));

        vendor.deactivate();

        vendorRepository.save(vendor);
    }
}