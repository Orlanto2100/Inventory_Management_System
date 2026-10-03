package com.percy.inventory.Location.dto;

import com.percy.inventory.Location.LocationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LocationCreateRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Size(max = 50)
        String code,

        @NotNull
        LocationType type,

        @NotNull
        Long warehouseId
) {}