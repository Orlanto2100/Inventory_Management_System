package com.percy.inventory.Repository;

import com.percy.inventory.Model.entity.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
}
