package com.percy.inventory.Warehouse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    boolean existsByCodeIgnoreCase(String code);

    @Query("""
            SELECT w
            FROM Warehouse w
            WHERE (
                :search = ''
                OR LOWER(w.code) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(w.name) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(w.city) LIKE LOWER(CONCAT('%', :search, '%'))
            )
            AND w.status = :status
            """)
    Page<Warehouse> searchWithStatus(
            @Param("search") String search,
            @Param("status") WarehouseStatus status,
            Pageable pageable
    );

    @Query("""
            SELECT w
            FROM Warehouse w
            WHERE (
                :search = ''
                OR LOWER(w.code) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(w.name) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(w.city) LIKE LOWER(CONCAT('%', :search, '%'))
            )
            """)
    Page<Warehouse> searchWithoutStatus(
            @Param("search") String search,
            Pageable pageable
    );
}