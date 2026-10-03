package com.percy.inventory.PurchaseRequest;

import com.percy.inventory.BaseEntity;
import com.percy.inventory.Products.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "purchase_request_lines")
@Getter
@NoArgsConstructor
public class PurchaseRequestLine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purchase_request_id", nullable = false)
    private PurchaseRequest purchaseRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal quantity;

    @Column(nullable = false, length = 30)
    private String unit;

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(length = 500)
    private String notes;

    public PurchaseRequestLine(
            PurchaseRequest purchaseRequest,
            Product product,
            String description,
            BigDecimal quantity,
            String unit,
            LocalDate requiredDate,
            String notes
    ) {
        this.purchaseRequest = purchaseRequest;
        this.product = product;
        this.description = description;
        this.quantity = quantity;
        this.unit = unit;
        this.requiredDate = requiredDate;
        this.notes = notes;
    }
}