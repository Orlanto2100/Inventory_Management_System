package com.percy.inventory.Repository;

import com.percy.inventory.Model.entity.PurchaseOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderLineRepository extends JpaRepository<PurchaseOrderLine, Integer> {
}
