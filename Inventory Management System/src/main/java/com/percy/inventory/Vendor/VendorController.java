package com.percy.inventory.Vendor;

import com.percy.inventory.Vendor.dto.CreateVendorRequest;
import com.percy.inventory.Vendor.dto.UpdateVendorRequest;
import com.percy.inventory.Vendor.dto.VendorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {
    private final VendorService vendorService;
    @PostMapping 
    public VendorResponse createVendor(@RequestBody CreateVendorRequest request) {
        return vendorService.createVendor(request);
    }

    @GetMapping("/id")
    public VendorResponse getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @GetMapping("/name/{name}")
    public  VendorResponse getVendorByName(@PathVariable String name){
        return vendorService.getVendorByName(name);
    }

    @GetMapping
    public List<VendorResponse> getVendors(){
        return vendorService.getAllVendors();
    }

    @PatchMapping("/{id}")
    public VendorResponse updateVendor(@PathVariable Long id, @RequestBody UpdateVendorRequest request){
        return vendorService.updateVendorById(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteVendorById(@PathVariable Long id){
        vendorService.deleteVendorById(id);
    }

}
