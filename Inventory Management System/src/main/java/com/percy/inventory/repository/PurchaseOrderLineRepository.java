package com.percy.inventory.repository;

import com.percy.inventory.model.entity.PurchaseOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderLineRepository extends JpaRepository<PurchaseOrderLine, Integer> {
}
