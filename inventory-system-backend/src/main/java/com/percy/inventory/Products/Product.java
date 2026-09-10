package com.percy.inventory.Products;

import com.percy.inventory.Inventory.Inventory;
import com.percy.inventory.BaseEntity;
import com.percy.inventory.SalesOrder.SalesOrderLine;
import com.percy.inventory.StockMovement.StockMovement;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, length = 100)
    private String productName;

    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "product")
    List<Inventory> inventories;

    @OneToMany(mappedBy = "product")
    List<StockMovement> stockMovement;

    @OneToMany(mappedBy = "product")
    private List<SalesOrderLine> salesOrderLines;
}
