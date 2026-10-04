package com.percy.inventory.Location;

import com.percy.inventory.BaseEntity;
import com.percy.inventory.Inventory.Inventory;
import com.percy.inventory.StockMovement.StockMovement;
import com.percy.inventory.Warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "locations",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_location_warehouse_code",
                        columnNames = {
                                "warehouse_id",
                                "code"
                        }
                )
        }
)
@Getter
@NoArgsConstructor
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column(
            nullable = false,
            length = 100
    )
    private String name;

    @Column(
            nullable = false,
            length = 50
    )
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private LocationType type;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private LocationStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "warehouse_id",
            nullable = false
    )
    private Warehouse warehouse;

    @OneToMany(
            mappedBy = "location",
            fetch = FetchType.LAZY
    )
    private final List<Inventory> inventories =
            new ArrayList<>();

    @OneToMany(
            mappedBy = "toLocation",
            fetch = FetchType.LAZY
    )
    private final List<StockMovement> inStockMovements =
            new ArrayList<>();

    @OneToMany(
            mappedBy = "fromLocation",
            fetch = FetchType.LAZY
    )
    private final List<StockMovement> outStockMovements =
            new ArrayList<>();

    public Location(
            String name,
            String code,
            LocationType type,
            Warehouse warehouse
    ) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.warehouse = warehouse;
        this.status = LocationStatus.ACTIVE;
    }

    public void update(
            String name,
            String code,
            LocationType type
    ) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }

        if (code != null && !code.isBlank()) {
            this.code = code;
        }

        if (type != null) {
            this.type = type;
        }
    }

    public void activate() {
        this.status = LocationStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = LocationStatus.INACTIVE;
    }
}