package com.percy.inventory.Warehouse;

import com.percy.inventory.Warehouse.dto.CreateWarehouseRequest;
import com.percy.inventory.Warehouse.dto.UpdateWarehouseRequest;
import com.percy.inventory.Warehouse.dto.WarehouseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
@Validated
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<WarehouseResponse> createWarehouse(
            @Valid @RequestBody CreateWarehouseRequest request) {

        WarehouseResponse response = warehouseService.createWarehouse(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public WarehouseResponse getWarehouseById(
            @PathVariable @Positive Long id) {

        return warehouseService.getWarehouseById(id);
    }

    @GetMapping("/name/{name}")
    public WarehouseResponse getWarehouseByName(
            @PathVariable String name) {

        return warehouseService.getWarehouseByName(name);
    }

    @GetMapping
    public List<WarehouseResponse> getAllWarehouses() {
        return warehouseService.getAllWarehouses();
    }

    @PatchMapping("/{id}")
    public WarehouseResponse updateWarehouse(
            @PathVariable @Positive Long id,
            @Valid @RequestBody UpdateWarehouseRequest request) {

        return warehouseService.updateWarehouseById(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(
            @PathVariable @Positive Long id) {

        warehouseService.deleteWarehouseById(id);

        return ResponseEntity.noContent().build();
    }
}