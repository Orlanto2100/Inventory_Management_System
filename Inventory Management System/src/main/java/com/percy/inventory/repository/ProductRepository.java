package com.percy.inventory.repository;

import com.percy.inventory.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product,Long> {

}
