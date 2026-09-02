package com.percy.inventory.Users;

import com.percy.inventory.Users.dto.CreateUserRequest;
import com.percy.inventory.Users.dto.UpdateUserProfileRequest;
import com.percy.inventory.Users.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserRequest request) {
        if (usersRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (usersRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        Users user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Users savedUser = usersRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    public UserResponse getUserById(Long id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toResponse(user);
    }

    public UserResponse getUserByEmail(String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toResponse(user);
    }

    public List<UserResponse> listUsers() {
        return usersRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public UserResponse updateUserProfile(Long id, UpdateUserProfileRequest request) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserMapper.updateEntity(user, request);
        Users updatedUser = usersRepository.save(user);

        return UserMapper.toResponse(updatedUser);
    }

    public UserResponse changePassword(Long id, String oldPassword, String newPassword) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        Users updatedUser = usersRepository.save(user);

        return UserMapper.toResponse(updatedUser);
    }

    public void deleteUserById(Long id) {
        if (!usersRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        usersRepository.deleteById(id);
    }
}
