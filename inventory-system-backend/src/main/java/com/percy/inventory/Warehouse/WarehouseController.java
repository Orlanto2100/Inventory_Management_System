package com.percy.inventory.Warehouse;

import com.percy.inventory.Warehouse.dto.CreateWarehouseRequest;
import com.percy.inventory.Warehouse.dto.UpdateWarehouseRequest;
import com.percy.inventory.Warehouse.dto.WarehouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping
    public WarehouseResponse createWarehouse(@RequestBody CreateWarehouseRequest request) {
        return warehouseService.createWarehouse(request);
    }

    @GetMapping("/{id}")
    public WarehouseResponse getWarehouseById(@PathVariable Long id) {
        return warehouseService.getWarehouseById(id);
    }

    @GetMapping("/name/{name}")
    public WarehouseResponse getWarehouseByName(@PathVariable String name) {
        return warehouseService.getWarehouseByName(name);
    }

    @GetMapping
    public List<WarehouseResponse> getAllWarehouses() {
        return warehouseService.getAllWarehouses();
    }

    @PatchMapping("/{id}")
    public WarehouseResponse updateWarehouse(
            @PathVariable Long id,
            @RequestBody UpdateWarehouseRequest request) {
        return warehouseService.updateWarehouseById(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouseById(id);
    }
}