package com.percy.inventory.Model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long LocationId;

    @Column(nullable = false)
    private String name;
    private String code;
    private String type;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "location")
    private List<Inventory> inventories;

    @OneToMany(mappedBy = "toLocation")
    private List<StockMovement> inStockMovements;

    @OneToMany(mappedBy = "fromLocation")
    private List<StockMovement> outStockMovements;
}
