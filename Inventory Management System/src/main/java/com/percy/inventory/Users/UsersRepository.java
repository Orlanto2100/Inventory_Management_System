package com.percy.inventory.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Users> findByEmail(String email);
}
