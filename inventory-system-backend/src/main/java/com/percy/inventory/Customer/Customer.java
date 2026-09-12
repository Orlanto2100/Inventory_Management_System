package com.percy.inventory.Customer;

import com.percy.inventory.BaseEntity;
import com.percy.inventory.SalesOrder.SalesOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor
public class Customer extends BaseEntity {

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

    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.LAZY
    )
    private List<SalesOrder> salesOrders = new ArrayList<>();

    public Customer(
            String name,
            String phone,
            String email,
            String address
    ) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
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
}