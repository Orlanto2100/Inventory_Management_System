package com.percy.inventory.PurchaseOrder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderLineRepository extends JpaRepository<PurchaseOrderLine, Integer> {
}
