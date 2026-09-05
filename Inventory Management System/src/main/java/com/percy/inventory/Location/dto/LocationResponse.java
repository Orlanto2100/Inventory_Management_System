package com.percy.inventory.Location.dto;

import com.percy.inventory.Warehouse.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LocationResponse {
    private Long LocationId;
    private String name;
    private String code;
    private String type;
    private Warehouse warehouse;
}
