package com.percy.inventory.PurchaseRequest;

import com.percy.inventory.BaseEntity;
import com.percy.inventory.Location.Location;
import com.percy.inventory.Users.Users;
import com.percy.inventory.Warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_requests")
@Getter
@NoArgsConstructor
public class PurchaseRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "request_no",
            nullable = false,
            unique = true,
            length = 50
    )
    private String requestNo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private Users requester;

    @Column(nullable = false, length = 100)
    private String department;

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Column(name = "required_date", nullable = false)
    private LocalDate requiredDate;

    @Column(nullable = false, length = 500)
    private String reason;

    @Column(length = 1000)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PurchaseRequestStatus status;

    @OneToMany(
            mappedBy = "purchaseRequest",
            fetch = FetchType.LAZY
    )
    private List<PurchaseRequestLine> lines =
            new ArrayList<>();

    public PurchaseRequest(
            String requestNo,
            Users requester,
            String department,
            LocalDate requestDate,
            LocalDate requiredDate,
            String reason,
            String notes,
            Warehouse warehouse,
            Location location
    ) {
        this.requestNo = requestNo;
        this.requester = requester;
        this.department = department;
        this.requestDate = requestDate;
        this.requiredDate = requiredDate;
        this.reason = reason;
        this.notes = notes;
        this.warehouse = warehouse;
        this.location = location;
        this.status = PurchaseRequestStatus.DRAFT;
    }

    public void update(
            String department,
            LocalDate requiredDate,
            String reason,
            String notes,
            Warehouse warehouse,
            Location location
    ) {
        this.department = department;
        this.requiredDate = requiredDate;
        this.reason = reason;
        this.notes = notes;
        this.warehouse = warehouse;
        this.location = location;
    }

    public void submit() {
        this.status = PurchaseRequestStatus.PENDING_APPROVAL;
    }

    public void approve() {
        this.status = PurchaseRequestStatus.APPROVED;
    }

    public void reject() {
        this.status = PurchaseRequestStatus.REJECTED;
    }

    public void process() {
        this.status = PurchaseRequestStatus.PROCESSING;
    }

    public void complete() {
        this.status = PurchaseRequestStatus.COMPLETED;
    }
}
