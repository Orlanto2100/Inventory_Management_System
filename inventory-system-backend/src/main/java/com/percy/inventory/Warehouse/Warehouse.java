package com.percy.inventory.Warehouse;

import com.percy.inventory.BaseEntity;
import com.percy.inventory.Location.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "warehouses")
@Getter
@NoArgsConstructor
public class Warehouse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 250)
    private String address;

    @Column(length = 100)
    private String city;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 254)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private WarehouseStatus status;

    @OneToMany(
            mappedBy = "warehouse",
            fetch = FetchType.LAZY
    )
    private final List<Location> locations = new ArrayList<>();

    public Warehouse(
            String code,
            String name,
            String address,
            String city,
            String phoneNumber,
            String email
    ) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = WarehouseStatus.ACTIVE;
    }

    public void update(
            String code,
            String name,
            String address,
            String city,
            String phoneNumber,
            String email
    ) {
        if (code != null) {
            this.code = code;
        }

        if (name != null) {
            this.name = name;
        }

        if (address != null) {
            this.address = address;
        }

        if (city != null) {
            this.city = city;
        }

        if (phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }

        if (email != null) {
            this.email = email;
        }
    }

    public void deactivate() {
        this.status = WarehouseStatus.INACTIVE;
    }

    public void activate() {
        this.status = WarehouseStatus.ACTIVE;
    }
}