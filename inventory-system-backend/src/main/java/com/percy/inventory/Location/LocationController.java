package com.percy.inventory.Location;

import com.percy.inventory.Location.dto.LocationCreateRequest;
import com.percy.inventory.Location.dto.LocationResponse;
import com.percy.inventory.Location.dto.LocationUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationResponse> create(
            @Valid @RequestBody LocationCreateRequest request
    ) {
        LocationResponse response =
                locationService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationResponse> getById(
            @PathVariable Long locationId
    ) {
        return ResponseEntity.ok(
                locationService.getById(locationId)
        );
    }

    @GetMapping
    public ResponseEntity<Page<LocationResponse>> search(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) LocationType type,
            @RequestParam(required = false) LocationStatus status,
            @RequestParam(required = false) Long warehouseId,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(
                locationService.search(
                        search,
                        type,
                        status,
                        warehouseId,
                        pageable
                )
        );
    }

    @PatchMapping("/{locationId}")
    public ResponseEntity<LocationResponse> update(
            @PathVariable Long locationId,
            @Valid @RequestBody LocationUpdateRequest request
    ) {
        return ResponseEntity.ok(
                locationService.update(
                        locationId,
                        request
                )
        );
    }

    @PatchMapping("/{locationId}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable Long locationId
    ) {
        locationService.deactivate(locationId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/{locationId}/activate")
    public ResponseEntity<Void> activate(
            @PathVariable Long locationId
    ) {
        locationService.activate(locationId);

        return ResponseEntity
                .noContent()
                .build();
    }
}