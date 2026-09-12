package com.percy.inventory.Vendor;

import com.percy.inventory.Vendor.dto.CreateVendorRequest;
import com.percy.inventory.Vendor.dto.UpdateVendorRequest;
import com.percy.inventory.Vendor.dto.VendorResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
@Validated
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
    public VendorResponse getVendorById(
            @PathVariable @Positive Long id) {

        return vendorService.getVendorById(id);
    }

    @GetMapping("/name/{name}")
    public VendorResponse getVendorByName(
            @PathVariable String name) {

        return vendorService.getVendorByName(name);
    }

    @GetMapping
    public List<VendorResponse> getVendors() {
        return vendorService.getAllVendors();
    }

    @PatchMapping("/{id}")
    public VendorResponse updateVendor(
            @PathVariable @Positive Long id,
            @Valid @RequestBody UpdateVendorRequest request) {

        return vendorService.updateVendorById(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendorById(
            @PathVariable @Positive Long id) {

        vendorService.deleteVendorById(id);

        return ResponseEntity.noContent().build();
    }
}