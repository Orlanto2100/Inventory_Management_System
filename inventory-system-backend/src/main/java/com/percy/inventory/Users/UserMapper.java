package com.percy.inventory.Users;

import com.percy.inventory.Users.dto.CreateUserRequest;
import com.percy.inventory.Users.dto.UpdateUserProfileRequest;
import com.percy.inventory.Users.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(Users user) {
        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getAccountType(),
                user.getRole()
        );
    }

    public Users toEntity(CreateUserRequest request) {
        return new Users(
                request.username(),
                request.password(),
                request.fullName(),
                request.email(),
                request.accountType(),
                request.role()
        );
    }

    public void updateEntity(
            Users user,
            UpdateUserProfileRequest request
    ) {
        user.updateProfile(
                request.username(),
                request.fullName(),
                request.email()
        );
    }
}