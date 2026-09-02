package com.percy.inventory.StockMovement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovement, Integer> {
}
