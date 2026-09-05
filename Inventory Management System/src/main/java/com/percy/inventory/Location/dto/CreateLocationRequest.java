package com.percy.inventory.Location.dto;

import com.percy.inventory.Warehouse.Warehouse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLocationRequest {
    private String name;
    private String code;
    private String type;
    private Warehouse warehouse;
}
