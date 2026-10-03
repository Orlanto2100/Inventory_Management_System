package com.percy.inventory.Location;

import com.percy.inventory.Location.dto.LocationCreateRequest;
import com.percy.inventory.Location.dto.LocationResponse;
import com.percy.inventory.Location.dto.LocationUpdateRequest;
import com.percy.inventory.Warehouse.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public Location toEntity(
            LocationCreateRequest request,
            Warehouse warehouse
    ) {
        return new Location(
                request.name(),
                request.code(),
                request.type(),
                warehouse
        );
    }

    public LocationResponse toResponse(Location location) {
        return new LocationResponse(
                location.getLocationId(),
                location.getName(),
                location.getCode(),
                location.getType(),
                location.getStatus(),
                location.getWarehouse().getWarehouseId()
        );
    }

    public void updateEntity(
            Location location,
            LocationUpdateRequest request
    ) {
        location.update(
                request.name(),
                request.code(),
                request.type()
        );
    }
}