package com.percy.inventory.Location;

import com.percy.inventory.Location.dto.LocationCreateRequest;
import com.percy.inventory.Location.dto.LocationResponse;
import com.percy.inventory.Location.dto.LocationUpdateRequest;
import com.percy.inventory.Warehouse.Warehouse;
import com.percy.inventory.Warehouse.WarehouseRepository;
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
public class LocationService {

    private final LocationRepository locationRepository;
    private final WarehouseRepository warehouseRepository;
    private final LocationMapper locationMapper;

    @Transactional
    public LocationResponse create(LocationCreateRequest request) {

        Warehouse warehouse = warehouseRepository.findById(request.warehouseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found with id: " + request.warehouseId()
                        )
                );

        if (locationRepository.existsByWarehouse_WarehouseIdAndCodeIgnoreCase(
                request.warehouseId(),
                request.code()
        )) {
            throw new DuplicateResourceException(
                    "Location code already exists in this warehouse: " + request.code()
            );
        }

        Location location = locationMapper.toEntity(request, warehouse);

        Location savedLocation = locationRepository.save(location);

        return locationMapper.toResponse(savedLocation);
    }

    public LocationResponse getById(Long locationId) {
        Location location = findById(locationId);
        return locationMapper.toResponse(location);
    }

    public Page<LocationResponse> search(
            String search,
            LocationType type,
            LocationStatus status,
            Long warehouseId,
            Pageable pageable
    ) {
        return locationRepository
                .search(search, type, status, warehouseId, pageable)
                .map(locationMapper::toResponse);
    }

    @Transactional
    public LocationResponse update(
            Long locationId,
            LocationUpdateRequest request
    ) {
        Location location = findById(locationId);

        if (request.code() != null
                && !request.code().equalsIgnoreCase(location.getCode())
                && locationRepository.existsByWarehouse_WarehouseIdAndCodeIgnoreCase(
                location.getWarehouse().getWarehouseId(),
                request.code()
        )) {

            throw new DuplicateResourceException(
                    "Location code already exists in this warehouse: " + request.code()
            );
        }

        locationMapper.updateEntity(location, request);

        return locationMapper.toResponse(location);
    }

    @Transactional
    public void deactivate(Long locationId) {
        Location location = findById(locationId);
        location.deactivate();
    }

    @Transactional
    public void activate(Long locationId) {
        Location location = findById(locationId);
        location.activate();
    }

    private Location findById(Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Location not found with id: " + locationId
                        )
                );
    }
}