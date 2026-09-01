package com.percy.inventory.Model.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorResponse {
    private Long vendorId;
    private String name;
    private String phone;
    private String email;
    private String address;
}
