package com.percy.inventory.Controller;

import com.percy.inventory.Model.dto.Request.ChangePasswordRequest;
import com.percy.inventory.Model.dto.Request.CreateUserRequest;
import com.percy.inventory.Model.dto.Request.UpdateUserProfileRequest;
import com.percy.inventory.Model.dto.Response.UserResponse;
import com.percy.inventory.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public UserResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping
    public List<UserResponse> listUsers() {
        return userService.listUsers();
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserProfileRequest request) {
        return userService.updateUserProfile(id, request);
    }

    @PutMapping("/{id}/password")
    public UserResponse changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request) {

        return userService.changePassword(
                id,
                request.getOldPassword(),
                request.getNewPassword()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
