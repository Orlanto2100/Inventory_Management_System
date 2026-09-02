package com.percy.inventory.Vendor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor,Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    Optional<Vendor> findByName(String name);
}
