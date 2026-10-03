package com.percy.inventory.Warehouse.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record WarehouseUpdateRequest(

        @Size(max = 50)
        String code,

        @Size(max = 100)
        String name,

        @Size(max = 250)
        String address,

        @Size(max = 100)
        String city,

        @Size(max = 20)
        String phoneNumber,

        @Email
        @Size(max = 254)
        String email
) {
}