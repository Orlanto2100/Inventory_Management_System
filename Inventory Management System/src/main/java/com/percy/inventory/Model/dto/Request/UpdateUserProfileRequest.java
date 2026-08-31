package com.percy.inventory.Model.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserProfileRequest {
    private String username;
    private String fullName;
    private String email;
}
