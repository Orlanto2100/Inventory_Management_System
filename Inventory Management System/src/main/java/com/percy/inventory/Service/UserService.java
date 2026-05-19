package com.percy.inventory.Service;

import com.percy.inventory.Model.dto.Request.CreateUserRequest;
import com.percy.inventory.Model.dto.Request.UpdateUserProfileRequest;
import com.percy.inventory.Model.Mappers.UserMapper;
import com.percy.inventory.Model.dto.Response.UserResponse;
import com.percy.inventory.Model.entity.Users;
import com.percy.inventory.Repository.UsersRepository;
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
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        Users updatedUser = usersRepository.save(user);

        return UserMapper.toResponse(updatedUser);
    }

    public void changePassword(Long id, String oldPassword, String newPassword) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(user);
    }

    public void deleteUserById(Long id) {
        if (!usersRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        usersRepository.deleteById(id);
    }
}
