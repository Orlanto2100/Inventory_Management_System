package com.percy.inventory.Location.dto;

import com.percy.inventory.Location.LocationStatus;
import com.percy.inventory.Location.LocationType;

public record LocationResponse(

        Long locationId,

        String name,

        String code,

        LocationType type,

        LocationStatus status,

        Long warehouseId,

        String warehouseName

) {
}