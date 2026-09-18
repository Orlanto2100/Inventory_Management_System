package com.percy.inventory.Rfqs;

import com.percy.inventory.BaseEntity;
import com.percy.inventory.Vendor.Vendor;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "request_for_quotations")
@Getter
@NoArgsConstructor
public class RequestForQuotation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String rfqNumber;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false)
    private LocalDateTime requestDate;

    @Column(nullable = false)
    private LocalDateTime responseDeadline;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RfqStatus status;

    @ManyToMany
    @JoinTable(
            name = "rfq_vendors",
            joinColumns = @JoinColumn(name = "rfq_id"),
            inverseJoinColumns = @JoinColumn(name = "vendor_id")
    )
    private List<Vendor> vendors = new ArrayList<>();

    @OneToMany(
            mappedBy = "rfq",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RequestForQuotationLine> lines = new ArrayList<>();

    public RequestForQuotation(
            String rfqNumber,
            String title,
            LocalDateTime requestDate,
            LocalDateTime responseDeadline,
            String description,
            RfqStatus status
    ) {
        this.rfqNumber = rfqNumber;
        this.title = title;
        this.requestDate = requestDate;
        this.responseDeadline = responseDeadline;
        this.description = description;
        this.status = status;
    }

    public void update(
            String title,
            LocalDateTime responseDeadline,
            String description
    ) {
        if (title != null) {
            this.title = title;
        }

        if (responseDeadline != null) {
            this.responseDeadline = responseDeadline;
        }

        if (description != null) {
            this.description = description;
        }
    }

    public void addVendor(Vendor vendor) {
        this.vendors.add(vendor);
    }

    public void removeVendor(Vendor vendor) {
        this.vendors.remove(vendor);
    }

    public void addLine(RequestForQuotationLine line) {
        this.lines.add(line);
    }

    public void removeLine(RequestForQuotationLine line) {
        this.lines.remove(line);
    }

    public void changeStatus(RfqStatus status) {
        this.status = status;
    }
}