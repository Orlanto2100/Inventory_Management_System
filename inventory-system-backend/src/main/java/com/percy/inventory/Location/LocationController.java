package com.percy.inventory.Location;

import com.percy.inventory.Location.dto.CreateLocationRequest;
import com.percy.inventory.Location.dto.LocationResponse;
import com.percy.inventory.Location.dto.UpdateLocationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    public LocationResponse createLocation(@RequestBody CreateLocationRequest request) {
        return locationService.createLocation(request);
    }

    @GetMapping("/{id}")
    public LocationResponse getLocationById(@RequestParam Long id) {
        return locationService.getLocationById(id);
    }

    @GetMapping("/code/{code}")
    public LocationResponse getLocationByName(@PathVariable String code) {
        return locationService.getLocationByCode(code);
    }

    @GetMapping
    public List<LocationResponse> getLocations() {
        return locationService.getAllLocations();
    }

    @PatchMapping("/{id}")
    public LocationResponse updateLocation(@PathVariable Long id, @RequestBody UpdateLocationRequest request) {
        return locationService.updateLocation(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteLocationById(@PathVariable Long id) {
        locationService.deleteLocation(id);
    }
}
