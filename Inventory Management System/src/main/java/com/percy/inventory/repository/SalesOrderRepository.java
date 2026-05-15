package com.percy.inventory.repository;

import com.percy.inventory.model.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {
}
