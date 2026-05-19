package com.percy.inventory.Service;

import com.percy.inventory.Model.dto.Request.CreateWarehouseRequest;
import com.percy.inventory.Model.entity.Warehouse;
import com.percy.inventory.Repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public Warehouse createWarehouse(CreateWarehouseRequest  Request) {

        Warehouse warehouse = new Warehouse();
        warehouse.setEmail(Request.getEmail());
        warehouse.setName(Request.getName());
        warehouse.setCity(Request.getCity());
        warehouse.setCode(Request.getCode());
        warehouse.setPhoneNumber(Request.getPhoneNumber());
        return warehouseRepository.save(warehouse);
    }
}
