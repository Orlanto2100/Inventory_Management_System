package com.percy.inventory.Warehouse;

import com.percy.inventory.Warehouse.dto.CreateWarehouseRequest;
import com.percy.inventory.Warehouse.dto.UpdateWarehouseRequest;
import com.percy.inventory.Warehouse.dto.WarehouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public WarehouseResponse createWarehouse(CreateWarehouseRequest request) {
        Warehouse warehouse = WarehouseMapper.toEntity(request);
        Warehouse savedWareHouse = warehouseRepository.save(warehouse);

        return WarehouseMapper.toResponse(savedWareHouse);
    }

    public WarehouseResponse getWarehouseById(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        return WarehouseMapper.toResponse(warehouse);
    }

    public WarehouseResponse getWarehouseByName(String name) {
        Warehouse warehouse = warehouseRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        return WarehouseMapper.toResponse(warehouse);
    }

    public List<WarehouseResponse> getAllWarehouses() {
        return warehouseRepository.findAll()
                .stream()
                .map(WarehouseMapper::toResponse)
                .toList();
    }

    public WarehouseResponse updateWarehouseById(Long warehouseId, UpdateWarehouseRequest request) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        WarehouseMapper.updateEntity(warehouse,request);

        Warehouse savedWareHouse = warehouseRepository.save(warehouse);
        return  WarehouseMapper.toResponse(savedWareHouse);
    }

    public void  deleteWarehouseById(Long warehouseId) {
        warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        warehouseRepository.deleteById(warehouseId);
    }
}
