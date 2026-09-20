package com.percy.inventory.Users;

import com.percy.inventory.Users.dto.ChangePasswordRequest;
import com.percy.inventory.Users.dto.CreateUserRequest;
import com.percy.inventory.Users.dto.UpdateUserProfileRequest;
import com.percy.inventory.Users.dto.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {
        UserResponse response =
                userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(
            @PathVariable @Positive Long id
    ) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserResponse> listUsers() {
        return userService.listUsers();
    }

    @PatchMapping("/{id}")
    public UserResponse updateUserProfile(
            @PathVariable @Positive Long id,
            @Valid @RequestBody UpdateUserProfileRequest request
    ) {
        return userService.updateUserProfile(
                id,
                request
        );
    }

    @PatchMapping("/{id}/password")
    public UserResponse changePassword(
            @PathVariable @Positive Long id,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        return userService.changePassword(
                id,
                request.oldPassword(),
                request.newPassword()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(
            @PathVariable @Positive Long id
    ) {
        userService.deleteUserById(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}