package com.percy.inventory.Location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<Location, Long> {

    boolean existsByWarehouse_WarehouseIdAndCodeIgnoreCase(
            Long warehouseId,
            String code
    );

    @Query("""
            SELECT l
            FROM Location l
            WHERE (:search IS NULL
                   OR LOWER(l.code) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(l.name) LIKE LOWER(CONCAT('%', :search, '%')))
              AND (:type IS NULL OR l.type = :type)
              AND (:status IS NULL OR l.status = :status)
              AND (:warehouseId IS NULL
                   OR l.warehouse.warehouseId = :warehouseId)
            """)
    Page<Location> search(
            @Param("search") String search,
            @Param("type") LocationType type,
            @Param("status") LocationStatus status,
            @Param("warehouseId") Long warehouseId,
            Pageable pageable
    );
}