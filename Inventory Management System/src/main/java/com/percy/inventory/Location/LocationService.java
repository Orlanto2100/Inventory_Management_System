package com.percy.inventory.Location;

import com.percy.inventory.Location.dto.CreateLocationRequest;
import com.percy.inventory.Location.dto.LocationResponse;
import com.percy.inventory.Location.dto.UpdateLocationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationResponse createLocation(CreateLocationRequest request) {
        Location location = LocationMapper.toEntity(request);
        Location savedLocation = locationRepository.save(location);

        return LocationMapper.toResponse(savedLocation);
    }

    public LocationResponse getLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("location not found"));

        return LocationMapper.toResponse(location);
    }

    public LocationResponse getLocationByCode(String code) {
        Location location = locationRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("location not found"));

        return LocationMapper.toResponse(location);
    }

    public List<LocationResponse> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(LocationMapper::toResponse)
                .toList();
    }

    public LocationResponse updateLocation(Long id,UpdateLocationRequest request) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("location not found"));
        LocationMapper.updateEntity(location, request);
        Location updatedLocation = locationRepository.save(location);

        return LocationMapper.toResponse(updatedLocation);
    }

    public void deleteLocation(Long id) {
        if  (!locationRepository.existsById(id)) {
            throw new RuntimeException("Vendor with this location does not exist");
        }

        locationRepository.deleteById(id);
    }
}
