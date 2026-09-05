package com.percy.inventory.Location;

import com.percy.inventory.Location.dto.CreateLocationRequest;
import com.percy.inventory.Location.dto.LocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationResponse createLocation(CreateLocationRequest request) {
        Location location = LocationMapper.toEntity(request);
        Location savedLocation = locationRepository.save(location);

        return LocationMapper.toResponse(savedLocation);
    }
}
