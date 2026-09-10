package com.percy.inventory.Vendor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateVendorRequest {
    private String name;
    private String phone;
    private String email;
    private String address;
}
