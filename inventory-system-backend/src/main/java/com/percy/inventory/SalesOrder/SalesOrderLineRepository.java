package com.percy.inventory.SalesOrder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderLineRepository extends JpaRepository<SalesOrderLine, Integer> {
}
