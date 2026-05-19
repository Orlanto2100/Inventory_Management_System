package com.percy.inventory.Repository;

import com.percy.inventory.Model.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor,Long> {

}
