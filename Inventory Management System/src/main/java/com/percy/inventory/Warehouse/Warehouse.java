package com.percy.inventory.Warehouse;

import com.percy.inventory.BaseEntity;
import com.percy.inventory.Location.Location;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 250)
    private String address;

    @Column(length = 100)
    private String city;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 254)
    private String email;

    @OneToMany(
            mappedBy = "warehouse",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Location> locations = new ArrayList<>();
}
