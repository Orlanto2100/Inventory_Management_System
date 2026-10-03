package com.percy.inventory.PurchaseRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRequestRepository
        extends JpaRepository<PurchaseRequest, Long> {

    List<PurchaseRequest> findByWarehouseWarehouseId(
            Long warehouseId
    );
}