package com.percy.inventory.Warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResponse {
    private Long warehouseId;
    private String code;
    private String name;
    private String address;
    private String city;
    private String phoneNumber;
    private String email;
}
