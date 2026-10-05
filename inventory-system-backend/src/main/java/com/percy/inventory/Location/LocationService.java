package com.percy.inventory.Location;

import com.percy.inventory.Location.dto.LocationCreateRequest;
import com.percy.inventory.Location.dto.LocationResponse;
import com.percy.inventory.Location.dto.LocationUpdateRequest;
import com.percy.inventory.Users.Role;
import com.percy.inventory.Users.Users;
import com.percy.inventory.Users.UsersRepository;
import com.percy.inventory.Warehouse.Warehouse;
import com.percy.inventory.Warehouse.WarehouseRepository;
import com.percy.inventory.Warehouse.WarehouseStatus;
import com.percy.inventory.exception.DuplicateResourceException;
import com.percy.inventory.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

    private final LocationRepository locationRepository;
    private final WarehouseRepository warehouseRepository;
    private final UsersRepository usersRepository;
    private final LocationMapper locationMapper;

    @Transactional
    public LocationResponse create(
            LocationCreateRequest request
    ) {

        requireAdmin();

        Warehouse warehouse =
                findWarehouse(request.warehouseId());

        validateWarehouseActive(warehouse);

        String name =
                normalizeName(request.name());

        String code =
                normalizeCode(request.code());

        if (locationRepository
                .existsByWarehouse_WarehouseIdAndCodeIgnoreCase(
                        warehouse.getWarehouseId(),
                        code
                )) {

            throw new DuplicateResourceException(
                    "Location code already exists in this warehouse: "
                            + code
            );
        }

        Location location =
                new Location(
                        name,
                        code,
                        request.type(),
                        warehouse
                );

        Location savedLocation =
                locationRepository.save(location);

        return locationMapper.toResponse(
                savedLocation
        );
    }

    public LocationResponse getById(
            Long locationId
    ) {

        Location location =
                findById(locationId);

        validateViewAccess(location);

        return locationMapper.toResponse(
                location
        );
    }

    public Page<LocationResponse> search(
            String search,
            LocationType type,
            LocationStatus status,
            Long warehouseId,
            Pageable pageable
    ) {

        Users currentUser =
                getCurrentUser();

        Long effectiveWarehouseId =
                resolveWarehouseFilter(
                        currentUser,
                        warehouseId
                );

        String normalizedSearch =
                normalizeSearch(search);

        String typeValue =
                type == null
                        ? null
                        : type.name();

        String statusValue =
                status == null
                        ? null
                        : status.name();

        return locationRepository
                .search(
                        normalizedSearch,
                        typeValue,
                        statusValue,
                        effectiveWarehouseId,
                        pageable
                )
                .map(
                        locationMapper::toResponse
                );
    }

    @Transactional
    public LocationResponse update(
            Long locationId,
            LocationUpdateRequest request
    ) {

        requireAdmin();

        Location location =
                findById(locationId);

        String normalizedCode =
                request.code() == null
                        ? null
                        : normalizeCode(
                        request.code()
                );

        if (normalizedCode != null
                && !normalizedCode.equalsIgnoreCase(
                location.getCode()
        )
                && locationRepository
                .existsByWarehouse_WarehouseIdAndCodeIgnoreCase(
                        location.getWarehouse()
                                .getWarehouseId(),
                        normalizedCode
                )) {

            throw new DuplicateResourceException(
                    "Location code already exists in this warehouse: "
                            + normalizedCode
            );
        }

        LocationUpdateRequest normalizedRequest =
                new LocationUpdateRequest(
                        request.name() == null
                                ? null
                                : normalizeName(
                                request.name()
                        ),
                        normalizedCode,
                        request.type()
                );

        locationMapper.updateEntity(
                location,
                normalizedRequest
        );

        return locationMapper.toResponse(
                location
        );
    }

    @Transactional
    public void deactivate(
            Long locationId
    ) {

        requireAdmin();

        Location location =
                findById(locationId);

        location.deactivate();
    }

    @Transactional
    public void activate(
            Long locationId
    ) {

        requireAdmin();

        Location location =
                findById(locationId);

        validateWarehouseActive(
                location.getWarehouse()
        );

        location.activate();
    }

    private Location findById(
            Long locationId
    ) {

        return locationRepository
                .findById(locationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Location not found with id: "
                                        + locationId
                        )
                );
    }

    private Warehouse findWarehouse(
            Long warehouseId
    ) {

        return warehouseRepository
                .findById(warehouseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found with id: "
                                        + warehouseId
                        )
                );
    }

    private void validateWarehouseActive(
            Warehouse warehouse
    ) {

        if (warehouse.getStatus()
                != WarehouseStatus.ACTIVE) {

            throw new IllegalStateException(
                    "Location can only belong to an active warehouse"
            );
        }
    }

    private void validateViewAccess(
            Location location
    ) {

        Users currentUser =
                getCurrentUser();

        if (currentUser.getRole()
                == Role.ADMIN) {
            return;
        }

        if (currentUser.getRole()
                == Role.PURCHASING_STAFF) {
            return;
        }

        if (currentUser.getRole()
                != Role.WAREHOUSE_STAFF) {

            throw new AccessDeniedException(
                    "You do not have permission to view this location"
            );
        }

        if (currentUser.getWarehouse()
                == null) {

            throw new AccessDeniedException(
                    "Warehouse staff has no assigned warehouse"
            );
        }

        if (!currentUser
                .getWarehouse()
                .getWarehouseId()
                .equals(
                        location
                                .getWarehouse()
                                .getWarehouseId()
                )) {

            throw new AccessDeniedException(
                    "You can only access locations "
                            + "for your assigned warehouse"
            );
        }
    }

    private Long resolveWarehouseFilter(
            Users currentUser,
            Long requestedWarehouseId
    ) {

        if (currentUser.getRole()
                == Role.ADMIN) {

            return requestedWarehouseId;
        }

        if (currentUser.getRole()
                == Role.PURCHASING_STAFF) {

            return requestedWarehouseId;
        }

        if (currentUser.getRole()
                != Role.WAREHOUSE_STAFF) {

            throw new AccessDeniedException(
                    "You do not have permission to view locations"
            );
        }

        if (currentUser.getWarehouse()
                == null) {

            throw new AccessDeniedException(
                    "Warehouse staff has no assigned warehouse"
            );
        }

        Long assignedWarehouseId =
                currentUser
                        .getWarehouse()
                        .getWarehouseId();

        if (requestedWarehouseId != null
                && !assignedWarehouseId.equals(
                requestedWarehouseId
        )) {

            throw new AccessDeniedException(
                    "You can only access locations "
                            + "for your assigned warehouse"
            );
        }

        return assignedWarehouseId;
    }

    private Users getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()) {

            throw new AccessDeniedException(
                    "Authentication is required"
            );
        }

        String username =
                authentication.getName();

        return usersRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Current user not found"
                        )
                );
    }

    private void requireAdmin() {

        Users currentUser =
                getCurrentUser();

        if (currentUser.getRole()
                != Role.ADMIN) {

            throw new AccessDeniedException(
                    "Only administrators can modify locations"
            );
        }
    }

    private String normalizeName(
            String name
    ) {

        return name.trim();
    }

    private String normalizeCode(
            String code
    ) {

        return code
                .trim()
                .toUpperCase();
    }

    private String normalizeSearch(
            String search
    ) {

        if (search == null
                || search.isBlank()) {

            return null;
        }

        return search.trim();
    }
}