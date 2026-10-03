package com.percy.inventory.Warehouse;

import com.percy.inventory.Warehouse.dto.WarehouseCreateRequest;
import com.percy.inventory.Warehouse.dto.WarehouseResponse;
import com.percy.inventory.Warehouse.dto.WarehouseUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<WarehouseResponse> create(
            @Valid @RequestBody WarehouseCreateRequest request
    ) {
        WarehouseResponse response = warehouseService.create(request);

        return ResponseEntity
                .status(201)
                .body(response);
    }

    @GetMapping("/{warehouseId}")
    public ResponseEntity<WarehouseResponse> getById(
            @PathVariable Long warehouseId
    ) {
        return ResponseEntity.ok(
                warehouseService.getById(warehouseId)
        );
    }

    @GetMapping
    public ResponseEntity<Page<WarehouseResponse>> search(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) WarehouseStatus status,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(
                warehouseService.search(
                        search,
                        status,
                        pageable
                )
        );
    }

    @PatchMapping("/{warehouseId}")
    public ResponseEntity<WarehouseResponse> update(
            @PathVariable Long warehouseId,
            @Valid @RequestBody WarehouseUpdateRequest request
    ) {
        return ResponseEntity.ok(
                warehouseService.update(
                        warehouseId,
                        request
                )
        );
    }

    @PatchMapping("/{warehouseId}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable Long warehouseId
    ) {
        warehouseService.deactivate(warehouseId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{warehouseId}/activate")
    public ResponseEntity<Void> activate(
            @PathVariable Long warehouseId
    ) {
        warehouseService.activate(warehouseId);

        return ResponseEntity.noContent().build();
    }
}