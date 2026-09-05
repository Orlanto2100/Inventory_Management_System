package com.percy.inventory.Location;

import com.percy.inventory.Location.dto.CreateLocationRequest;
import com.percy.inventory.Location.dto.LocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    public LocationResponse createLocation(@RequestBody CreateLocationRequest request) {
        return locationService.createLocation(request);
    }
}
