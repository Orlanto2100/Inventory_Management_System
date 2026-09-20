package com.percy.inventory;

import com.percy.inventory.Users.AccountType;
import com.percy.inventory.Users.Role;
import com.percy.inventory.Users.Users;
import com.percy.inventory.Users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminDataInitializer implements CommandLineRunner {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        String username = "admin";

        if (usersRepository.existsByUsername(username)) {
            return;
        }

        Users admin = new Users(
                username,
                passwordEncoder.encode("admin12345"),
                "System Administrator",
                "admin@example.com",
                AccountType.COMPANY,
                Role.ADMIN
        );

        usersRepository.save(admin);

        System.out.println(
                "Default admin account created: username=admin"
        );
    }
}