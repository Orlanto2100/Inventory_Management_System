package com.percy.inventory.Location;

import com.percy.inventory.Location.dto.CreateLocationRequest;
import com.percy.inventory.Location.dto.LocationResponse;
import com.percy.inventory.Location.dto.UpdateLocationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(
            @Valid @RequestBody CreateLocationRequest request) {

        LocationResponse response = locationService.createLocation(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public LocationResponse getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    @GetMapping("/code/{code}")
    public LocationResponse getLocationByCode(@PathVariable String code) {
        return locationService.getLocationByCode(code);
    }

    @GetMapping
    public List<LocationResponse> getLocations() {
        return locationService.getAllLocations();
    }

    @PatchMapping("/{id}")
    public LocationResponse updateLocation(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLocationRequest request) {

        return locationService.updateLocation(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocationById(@PathVariable Long id) {
        locationService.deleteLocation(id);

        return ResponseEntity.noContent().build();
    }
}
