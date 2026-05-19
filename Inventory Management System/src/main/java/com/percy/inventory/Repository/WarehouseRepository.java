package com.percy.inventory.Repository;

import com.percy.inventory.Model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse,Long>{
}
