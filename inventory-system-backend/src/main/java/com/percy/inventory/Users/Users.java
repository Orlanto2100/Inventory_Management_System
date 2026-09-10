package com.percy.inventory.Users;

import com.percy.inventory.BaseEntity;
import com.percy.inventory.PurchaseOrder.PurchaseOrder;
import com.percy.inventory.SalesOrder.SalesOrder;
import com.percy.inventory.StockMovement.StockMovement;
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
    private Long userId;

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
