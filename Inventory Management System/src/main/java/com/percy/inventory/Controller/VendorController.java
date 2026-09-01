package com.percy.inventory.Controller;

import com.percy.inventory.Model.dto.Request.CreateVendorRequest;
import com.percy.inventory.Model.dto.Response.VendorResponse;
import com.percy.inventory.Service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {
    private final VendorService vendorService;
    @PostMapping
    public VendorResponse createVendor(@RequestBody CreateVendorRequest request) {
        return vendorService.createVendor(request);
    }
}
