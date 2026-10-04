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
                        CAST(:search AS TEXT) IS NULL
                        OR l.code ILIKE CONCAT(
                            '%',
                            CAST(:search AS TEXT),
                            '%'
                        )
                        OR l.name ILIKE CONCAT(
                            '%',
                            CAST(:search AS TEXT),
                            '%'
                        )
                    )
                    AND (
                        CAST(:type AS TEXT) IS NULL
                        OR l.type = CAST(:type AS TEXT)
                    )
                    AND (
                        CAST(:status AS TEXT) IS NULL
                        OR l.status = CAST(:status AS TEXT)
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
                        CAST(:search AS TEXT) IS NULL
                        OR l.code ILIKE CONCAT(
                            '%',
                            CAST(:search AS TEXT),
                            '%'
                        )
                        OR l.name ILIKE CONCAT(
                            '%',
                            CAST(:search AS TEXT),
                            '%'
                        )
                    )
                    AND (
                        CAST(:type AS TEXT) IS NULL
                        OR l.type = CAST(:type AS TEXT)
                    )
                    AND (
                        CAST(:status AS TEXT) IS NULL
                        OR l.status = CAST(:status AS TEXT)
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
            @Param("type") LocationType type,
            @Param("status") LocationStatus status,
            @Param("warehouseId") Long warehouseId,
            Pageable pageable
    );
}