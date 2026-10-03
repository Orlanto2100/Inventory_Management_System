package com.percy.inventory.Location.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLocationRequest {
    private String name;
    private String code;
    private String type;
}
