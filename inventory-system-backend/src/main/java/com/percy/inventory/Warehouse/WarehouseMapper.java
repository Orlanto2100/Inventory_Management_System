package com.percy.inventory.Warehouse;

import com.percy.inventory.Warehouse.dto.CreateWarehouseRequest;
import com.percy.inventory.Warehouse.dto.UpdateWarehouseRequest;
import com.percy.inventory.Warehouse.dto.WarehouseResponse;

public class WarehouseMapper {
    public static WarehouseResponse toResponse(Warehouse warehouse) {
        return new WarehouseResponse(
                warehouse.getWarehouseId(),
                warehouse.getCode(),
                warehouse.getName(),
                warehouse.getAddress(),
                warehouse.getCity(),
                warehouse.getPhoneNumber(),
                warehouse.getEmail()
        );
    }

    public static Warehouse toEntity(CreateWarehouseRequest request) {
        Warehouse warehouse = new Warehouse();
        warehouse.setCode(request.getCode());
        warehouse.setName(request.getName());
        warehouse.setCity(request.getCity());
        warehouse.setPhoneNumber(request.getPhoneNumber());
        warehouse.setEmail(request.getEmail());

        return warehouse;
    }

    public static void updateEntity(
            Warehouse warehouse,
            UpdateWarehouseRequest request) {

        if (request.getCode() != null) {
            warehouse.setCode(request.getCode());
        }

        if (request.getName() != null) {
            warehouse.setName(request.getName());
        }

        if (request.getAddress() != null) {
            warehouse.setAddress(request.getAddress());
        }

        if (request.getCity() != null) {
            warehouse.setCity(request.getCity());
        }

        if (request.getPhoneNumber() != null) {
            warehouse.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getEmail() != null) {
            warehouse.setEmail(request.getEmail());
        }
    }
}