package com.percy.inventory.model.entity;

import com.percy.inventory.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
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

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<PurchaseOrder> purchaseOrders;

    @OneToMany(mappedBy = "performedBy", fetch = FetchType.LAZY)
    private List<StockMovement> stockMovements;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<SalesOrder> salesOrders;
}
