package com.percy.inventory.repository;

import com.percy.inventory.model.entity.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
}
