package com.percy.inventory.Vendor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVendorRequest {
    private  String name;
    private String phone;
    private String email;
    private String address;
}
