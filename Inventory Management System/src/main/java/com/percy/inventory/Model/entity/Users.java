package com.percy.inventory.Model.entity;

import com.percy.inventory.Model.enums.Role;
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
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserId;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    private String fullName;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<PurchaseOrder> purchaseOrders;

    @OneToMany(mappedBy = "performedBy", fetch = FetchType.LAZY)
    private List<StockMovement> stockMovements;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<SalesOrder> salesOrders;
}
