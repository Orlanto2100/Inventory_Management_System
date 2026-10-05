package com.percy.inventory.Location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepository
        extends JpaRepository<Location, Long> {

    boolean existsByWarehouse_WarehouseIdAndCodeIgnoreCase(
            Long warehouseId,
            String code
    );

    @Query(
            value = """
                    SELECT
                        l.location_id,
                        l.name,
                        l.code,
                        l.type,
                        l.status,
                        l.warehouse_id,
                        l.created_at,
                        l.updated_at
                    FROM locations l
                    WHERE (
                        :search IS NULL
                        OR l.code ILIKE CONCAT(
                            '%',
                            :search,
                            '%'
                        )
                        OR l.name ILIKE CONCAT(
                            '%',
                            :search,
                            '%'
                        )
                    )
                    AND (
                        :type IS NULL
                        OR l.type = :type
                    )
                    AND (
                        :status IS NULL
                        OR l.status = :status
                    )
                    AND (
                        :warehouseId IS NULL
                        OR l.warehouse_id = :warehouseId
                    )
                    """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM locations l
                    WHERE (
                        :search IS NULL
                        OR l.code ILIKE CONCAT(
                            '%',
                            :search,
                            '%'
                        )
                        OR l.name ILIKE CONCAT(
                            '%',
                            :search,
                            '%'
                        )
                    )
                    AND (
                        :type IS NULL
                        OR l.type = :type
                    )
                    AND (
                        :status IS NULL
                        OR l.status = :status
                    )
                    AND (
                        :warehouseId IS NULL
                        OR l.warehouse_id = :warehouseId
                    )
                    """,
            nativeQuery = true
    )
    Page<Location> search(
            @Param("search") String search,
            @Param("type") String type,
            @Param("status") String status,
            @Param("warehouseId") Long warehouseId,
            Pageable pageable
    );
}