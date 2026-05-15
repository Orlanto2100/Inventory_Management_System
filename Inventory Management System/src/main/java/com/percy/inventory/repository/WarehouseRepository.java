package com.percy.inventory.repository;

import com.percy.inventory.model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse,Long>{
}
