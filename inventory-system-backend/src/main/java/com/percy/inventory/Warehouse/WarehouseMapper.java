package com.percy.inventory.Warehouse;

import com.percy.inventory.Warehouse.dto.WarehouseCreateRequest;
import com.percy.inventory.Warehouse.dto.WarehouseResponse;
import com.percy.inventory.Warehouse.dto.WarehouseUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {

    public Warehouse toEntity(WarehouseCreateRequest request) {
        return new Warehouse(
                request.code(),
                request.name(),
                request.address(),
                request.city(),
                request.phoneNumber(),
                request.email()
        );
    }

    public WarehouseResponse toResponse(Warehouse warehouse) {
        return new WarehouseResponse(
                warehouse.getWarehouseId(),
                warehouse.getCode(),
                warehouse.getName(),
                warehouse.getAddress(),
                warehouse.getCity(),
                warehouse.getPhoneNumber(),
                warehouse.getEmail(),
                warehouse.getStatus().name()        );
    }

    public void updateEntity(
            Warehouse warehouse,
            WarehouseUpdateRequest request
    ) {
        warehouse.update(
                request.code(),
                request.name(),
                request.address(),
                request.city(),
                request.phoneNumber(),
                request.email()
        );
    }
}