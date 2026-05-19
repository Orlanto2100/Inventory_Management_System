package com.percy.inventory.Repository;

import com.percy.inventory.Model.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {
}
