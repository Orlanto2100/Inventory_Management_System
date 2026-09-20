package com.percy.inventory.Users;

import com.percy.inventory.BaseEntity;
import com.percy.inventory.PurchaseOrder.PurchaseOrder;
import com.percy.inventory.SalesOrder.SalesOrder;
import com.percy.inventory.StockMovement.StockMovement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class Users extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 100)
    private String fullName;

    @Column(length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Role role;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private final List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    @OneToMany(mappedBy = "performedBy", fetch = FetchType.LAZY)
    private final List<StockMovement> stockMovements = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private final List<SalesOrder> salesOrders = new ArrayList<>();

    public Users(
            String username,
            String password,
            String fullName,
            String email,
            AccountType accountType,
            Role role
    ) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.accountType = accountType;
        this.role = role;
    }

    public void updateProfile(
            String username,
            String fullName,
            String email
    ) {
        if (username != null) {
            this.username = username;
        }

        if (fullName != null) {
            this.fullName = fullName;
        }

        if (email != null) {
            this.email = email;
        }
    }

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void changeRole(Role role) {
        if (role != null) {
            this.role = role;
        }
    }

    public void changeAccountType(AccountType accountType) {
        if (accountType != null) {
            this.accountType = accountType;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            return List.of();
        }

        return List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + role.name()
                )
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}