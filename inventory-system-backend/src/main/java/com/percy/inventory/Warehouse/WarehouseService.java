package com.percy.inventory.Warehouse;

import com.percy.inventory.Warehouse.dto.WarehouseCreateRequest;
import com.percy.inventory.Warehouse.dto.WarehouseResponse;
import com.percy.inventory.Warehouse.dto.WarehouseUpdateRequest;
import com.percy.inventory.exception.DuplicateResourceException;
import com.percy.inventory.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @Transactional
    public WarehouseResponse create(WarehouseCreateRequest request) {

        if (warehouseRepository.existsByCodeIgnoreCase(request.code())) {
            throw new DuplicateResourceException(
                    "Warehouse code already exists: " + request.code()
            );
        }

        Warehouse warehouse = warehouseMapper.toEntity(request);

        Warehouse savedWarehouse = warehouseRepository.save(warehouse);

        return warehouseMapper.toResponse(savedWarehouse);
    }

    public WarehouseResponse getById(Long warehouseId) {

        Warehouse warehouse = findById(warehouseId);

        return warehouseMapper.toResponse(warehouse);
    }

    public Page<WarehouseResponse> search(
            String search,
            WarehouseStatus status,
            Pageable pageable
    ) {
        if (search == null) {
            search = "";
        }

        Page<Warehouse> warehouses;

        if (status == null) {
            warehouses = warehouseRepository.searchWithoutStatus(
                    search,
                    pageable
            );
        } else {
            warehouses = warehouseRepository.searchWithStatus(
                    search,
                    status,
                    pageable
            );
        }

        return warehouses.map(warehouseMapper::toResponse);
    }

    @Transactional
    public WarehouseResponse update(
            Long warehouseId,
            WarehouseUpdateRequest request
    ) {
        Warehouse warehouse = findById(warehouseId);

        if (request.code() != null
                && !request.code().equalsIgnoreCase(warehouse.getCode())
                && warehouseRepository.existsByCodeIgnoreCase(request.code())) {

            throw new DuplicateResourceException(
                    "Warehouse code already exists: " + request.code()
            );
        }

        warehouseMapper.updateEntity(warehouse, request);

        return warehouseMapper.toResponse(warehouse);
    }

    @Transactional
    public void deactivate(Long warehouseId) {

        Warehouse warehouse = findById(warehouseId);

        warehouse.deactivate();
    }

    @Transactional
    public void activate(Long warehouseId) {

        Warehouse warehouse = findById(warehouseId);

        warehouse.activate();
    }

    private Warehouse findById(Long warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found with id: " + warehouseId
                        )
                );
    }
}