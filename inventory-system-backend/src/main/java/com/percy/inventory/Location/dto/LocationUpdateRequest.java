package com.percy.inventory.Location.dto;

import com.percy.inventory.Location.LocationType;
import jakarta.validation.constraints.Size;

public record LocationUpdateRequest(
        @Size(max = 100)
        String name,

        @Size(max = 50)
        String code,

        LocationType type
) {}