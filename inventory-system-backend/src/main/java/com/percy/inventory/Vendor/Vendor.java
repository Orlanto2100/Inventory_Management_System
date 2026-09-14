package com.percy.inventory.Vendor;

import com.percy.inventory.BaseEntity;
import com.percy.inventory.PurchaseOrder.PurchaseOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendors")
@Getter
@NoArgsConstructor
public class Vendor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(length = 250)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VendorStatus status;

    @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
    private final List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    public Vendor(
            String name,
            String phone,
            String email,
            String address
    ) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.status = VendorStatus.ACTIVE;
    }

    public void update(
            String name,
            String phone,
            String email,
            String address
    ) {
        if (name != null) {
            this.name = name;
        }

        if (phone != null) {
            this.phone = phone;
        }

        if (email != null) {
            this.email = email;
        }

        if (address != null) {
            this.address = address;
        }
    }

    public void deactivate() {
        this.status = VendorStatus.INACTIVE;
    }
}