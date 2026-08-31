package com.percy.inventory.Model.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWarehouseRequest {
    private String code;
    private String name;
    private String city;
    private String phoneNumber;
    private String email;
}
