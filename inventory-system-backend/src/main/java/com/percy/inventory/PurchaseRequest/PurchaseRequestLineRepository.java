package com.percy.inventory.PurchaseRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRequestLineRepository
        extends JpaRepository<PurchaseRequestLine, Long> {

    List<PurchaseRequestLine> findByPurchaseRequestId(Long purchaseRequestId);
}