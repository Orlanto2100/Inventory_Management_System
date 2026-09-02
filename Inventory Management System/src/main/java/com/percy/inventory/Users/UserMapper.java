package com.percy.inventory.Users;

import com.percy.inventory.Users.dto.CreateUserRequest;
import com.percy.inventory.Users.dto.UpdateUserProfileRequest;
import com.percy.inventory.Users.dto.UserResponse;

public final class UserMapper {
    public static UserResponse toResponse(Users user) {
        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public static Users toEntity(CreateUserRequest request) {
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        return user;
    }

    public static void updateEntity(Users user, UpdateUserProfileRequest request) {
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
    }

}