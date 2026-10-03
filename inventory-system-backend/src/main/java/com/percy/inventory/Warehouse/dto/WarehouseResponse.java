package com.percy.inventory.Warehouse.dto;

public record WarehouseResponse(
        Long warehouseId,
        String code,
        String name,
        String address,
        String city,
        String phoneNumber,
        String email,
        String status
) {
}