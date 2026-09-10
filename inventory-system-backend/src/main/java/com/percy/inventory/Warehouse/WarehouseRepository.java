package com.percy.inventory.Warehouse;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse,Long>{
    Optional<Warehouse> findByName(String name);

}
