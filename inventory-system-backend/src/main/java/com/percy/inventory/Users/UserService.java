package com.percy.inventory.Users;

import com.percy.inventory.Users.dto.CreateUserRequest;
import com.percy.inventory.Users.dto.UpdateUserProfileRequest;
import com.percy.inventory.Users.dto.UserResponse;
import com.percy.inventory.exception.DuplicateResourceException;
import com.percy.inventory.exception.InvalidPasswordException;
import com.percy.inventory.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResponse createUser(CreateUserRequest request) {
        validateUsername(request.username());
        validateEmail(request.email());
        validateAccountTypeAndRole(
                request.accountType(),
                request.role()
        );

        Users user = userMapper.toEntity(request);

        user.changePassword(
                passwordEncoder.encode(request.password())
        );

        Users savedUser = usersRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    public UserResponse getUserById(Long id) {
        Users user = findUserById(id);

        return userMapper.toResponse(user);
    }

    public UserResponse getUserByEmail(String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        return userMapper.toResponse(user);
    }

    public List<UserResponse> listUsers() {
        return usersRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponse updateUserProfile(
            Long id,
            UpdateUserProfileRequest request
    ) {
        Users user = findUserById(id);

        if (request.username() != null &&
                !request.username().equals(user.getUsername())) {

            validateUsername(request.username());
        }

        if (request.email() != null &&
                !request.email().equals(user.getEmail())) {

            validateEmail(request.email());
        }

        userMapper.updateEntity(user, request);

        Users updatedUser = usersRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }

    public UserResponse changePassword(
            Long id,
            String oldPassword,
            String newPassword
    ) {
        Users user = findUserById(id);

        if (!passwordEncoder.matches(
                oldPassword,
                user.getPassword()
        )) {
            throw new InvalidPasswordException(
                    "Old password is incorrect"
            );
        }

        user.changePassword(
                passwordEncoder.encode(newPassword)
        );

        Users updatedUser = usersRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }

    public UserResponse changeRole(
            Long id,
            Role role
    ) {
        Users user = findUserById(id);

        user.changeRole(role);

        Users updatedUser = usersRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }

    public UserResponse changeAccountType(
            Long id,
            AccountType accountType
    ) {
        Users user = findUserById(id);

        user.changeAccountType(accountType);

        Users updatedUser = usersRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }

    public void deleteUserById(Long id) {
        Users user = findUserById(id);

        if (user.getRole() == Role.ADMIN &&
                usersRepository.countByRole(Role.ADMIN) <= 1) {

            throw new IllegalStateException(
                    "The last admin cannot be deleted"
            );
        }

        usersRepository.delete(user);
    }

    private Users findUserById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );
    }

    private void validateUsername(String username) {
        if (usersRepository.existsByUsername(username)) {
            throw new DuplicateResourceException(
                    "Username already exists"
            );
        }
    }

    private void validateEmail(String email) {
        if (email != null &&
                usersRepository.existsByEmail(email)) {

            throw new DuplicateResourceException(
                    "Email already exists"
            );
        }
    }

    private void validateAccountTypeAndRole(
            AccountType accountType,
            Role role
    ) {
        if (accountType == AccountType.COMPANY &&
                role == null) {

            throw new IllegalArgumentException(
                    "Company users must have a role"
            );
        }

        if (accountType != AccountType.COMPANY &&
                role != null) {

            throw new IllegalArgumentException(
                    "Vendor and customer users cannot have a role"
            );
        }
    }
}