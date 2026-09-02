package com.percy.inventory.Model.dto.Request;

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
