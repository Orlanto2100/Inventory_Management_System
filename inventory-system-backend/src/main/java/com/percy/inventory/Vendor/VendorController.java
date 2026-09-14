package com.percy.inventory.Vendor;

import com.percy.inventory.Vendor.dto.CreateVendorRequest;
import com.percy.inventory.Vendor.dto.UpdateVendorRequest;
import com.percy.inventory.Vendor.dto.VendorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @PostMapping
    public ResponseEntity<VendorResponse> createVendor(
            @Valid @RequestBody CreateVendorRequest request) {

        VendorResponse response = vendorService.createVendor(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public VendorResponse getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @GetMapping
    public Page<VendorResponse> listVendors(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) VendorStatus status,
            @PageableDefault(size = 10) Pageable pageable) {

        return vendorService.listVendors(search, status, pageable);
    }

    @PatchMapping("/{id}")
    public VendorResponse updateVendor(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVendorRequest request) {

        return vendorService.updateVendor(id, request);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateVendor(
            @PathVariable Long id) {

        vendorService.deactivateVendor(id);

        return ResponseEntity.noContent().build();
    }
}