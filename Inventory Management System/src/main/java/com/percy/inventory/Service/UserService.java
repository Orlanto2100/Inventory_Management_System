package com.percy.inventory.Service;

import com.percy.inventory.model.dto.CreateUserRequest;
import com.percy.inventory.model.entity.Users;
import com.percy.inventory.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public Users CreateUser(CreateUserRequest request) {
        if (UsersRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);

        return UsersRepository.save(user);
    }
}
