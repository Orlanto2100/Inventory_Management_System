package com.percy.inventory.Location;

import com.percy.inventory.Location.dto.CreateLocationRequest;
import com.percy.inventory.Location.dto.LocationResponse;
import com.percy.inventory.Location.dto.UpdateLocationRequest;

public class LocationMapper {
    public static LocationResponse toResponse(Location location){
        return new LocationResponse(
                location.getLocationId(),
                location.getName(),
                location.getCode(),
                location.getType(),
                location.getWarehouse()
        );
    }

    public static Location toEntity(CreateLocationRequest request){
        Location location = new Location();
        location.setName(request.getName());
        location.setCode(request.getCode());
        location.setType(request.getType());
        location.setWarehouse(request.getWarehouse());

        return location;
    }

    public static Location updateEntity(Location location, UpdateLocationRequest request) {
        if (request.getName() != null) {
            location.setName(request.getName());
        }

        if (request.getCode() != null) {
            location.setCode(request.getCode());
        }

        if (request.getType() != null) {
            location.setType(request.getType());
        }
        return location;
    }
}
