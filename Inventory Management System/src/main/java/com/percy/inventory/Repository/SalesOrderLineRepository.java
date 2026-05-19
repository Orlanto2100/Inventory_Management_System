package com.percy.inventory.Repository;

import com.percy.inventory.Model.entity.SalesOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderLineRepository extends JpaRepository<SalesOrderLine, Integer> {
}
