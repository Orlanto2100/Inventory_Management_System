package com.percy.inventory.Model.dto.Response;

import com.percy.inventory.Model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
    public class UserResponse {
        private Long id;
        private String userName;
        private String fullName;
        private String email;
        private Role role;
    }
