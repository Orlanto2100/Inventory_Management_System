package com.percy.inventory.Model.entity;

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
public class Warehouse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long WarehouseId;

    @Column(unique = true)
    private String code;

    @Column(length = 100)
    private String name;

    @Column(length = 250)
    private String address;

    @Column(length = 20)
    private String city;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 20)
    private String email;

    @OneToMany(mappedBy = "warehouse")
    List<Location> locations;
}
