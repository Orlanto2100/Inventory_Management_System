package com.percy.inventory.Warehouse.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWarehouseRequest {
    private String code;
    private String name;
    private String address;
    private String city;
    private String phoneNumber;
    private String email;
}
