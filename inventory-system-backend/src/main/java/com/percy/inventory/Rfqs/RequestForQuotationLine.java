package com.percy.inventory.Rfqs;

import com.percy.inventory.BaseEntity;
import com.percy.inventory.Products.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "request_for_quotation_lines")
@Getter
@NoArgsConstructor
public class RequestForQuotationLine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rfq_id", nullable = false)
    private RequestForQuotation rfq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(length = 250)
    private String notes;

    public RequestForQuotationLine(
            RequestForQuotation rfq,
            Product product,
            BigDecimal quantity,
            String notes
    ) {
        this.rfq = rfq;
        this.product = product;
        this.quantity = quantity;
        this.notes = notes;
    }

    public void update(
            Product product,
            BigDecimal quantity,
            String notes
    ) {
        if (product != null) {
            this.product = product;
        }

        if (quantity != null) {
            this.quantity = quantity;
        }

        if (notes != null) {
            this.notes = notes;
        }
    }
}