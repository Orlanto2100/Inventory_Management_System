package com.percy.inventory.repository;

import com.percy.inventory.model.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor,Long> {

}
