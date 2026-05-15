package com.percy.inventory.repository;

import com.percy.inventory.model.entity.SalesOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderLineRepository extends JpaRepository<SalesOrderLine, Integer> {
}
