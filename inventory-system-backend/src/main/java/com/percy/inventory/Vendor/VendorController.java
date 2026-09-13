package com.percy.inventory.Vendor;

import com.percy.inventory.Vendor.dto.CreateVendorRequest;
import com.percy.inventory.Vendor.dto.UpdateVendorRequest;
import com.percy.inventory.Vendor.dto.VendorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<VendorResponse> listVendors() {
        return vendorService.listVendors();
    }

    @PatchMapping("/{id}")
    public VendorResponse updateVendor(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVendorRequest request) {

        return vendorService.updateVendor(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);

        return ResponseEntity.noContent().build();
    }
}