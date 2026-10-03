package com.percy.inventory.PurchaseRequest;

import com.percy.inventory.Location.Location;
import com.percy.inventory.Location.LocationRepository;
import com.percy.inventory.Location.LocationStatus;
import com.percy.inventory.Products.Product;
import com.percy.inventory.Products.ProductRepository;
import com.percy.inventory.PurchaseRequest.dto.CreatePurchaseRequestRequest;
import com.percy.inventory.PurchaseRequest.dto.PurchaseRequestLineRequest;
import com.percy.inventory.PurchaseRequest.dto.PurchaseRequestResponse;
import com.percy.inventory.PurchaseRequest.dto.UpdatePurchaseRequestRequest;
import com.percy.inventory.Users.Role;
import com.percy.inventory.Users.Users;
import com.percy.inventory.Users.UsersRepository;
import com.percy.inventory.Warehouse.Warehouse;
import com.percy.inventory.Warehouse.WarehouseRepository;
import com.percy.inventory.Warehouse.WarehouseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseRequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;
    private final PurchaseRequestLineRepository purchaseRequestLineRepository;

    private final UsersRepository usersRepository;
    private final ProductRepository productsRepository;
    private final WarehouseRepository warehouseRepository;
    private final LocationRepository locationRepository;

    private final PurchaseRequestMapper purchaseRequestMapper;

    @PreAuthorize("""
            hasAnyRole(
                'ADMIN',
                'WAREHOUSE_STAFF',
                'PURCHASING_STAFF'
            )
            """)
    public PurchaseRequestResponse create(
            CreatePurchaseRequestRequest request
    ) {

        Users requester = getCurrentUser();

        Warehouse warehouse =
                findWarehouse(request.warehouseId());

        validateWarehouseAccess(
                requester,
                warehouse
        );

        Location location =
                findLocation(
                        request.locationId(),
                        warehouse
                );

        PurchaseRequest purchaseRequest =
                new PurchaseRequest(
                        generateRequestNo(),
                        requester,
                        request.department(),
                        LocalDate.now(),
                        request.requiredDate(),
                        request.reason(),
                        request.notes(),
                        warehouse,
                        location
                );

        PurchaseRequest saved =
                purchaseRequestRepository.save(
                        purchaseRequest
                );

        saveLines(
                saved,
                request.lines()
        );

        return purchaseRequestMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("""
            hasAnyRole(
                'ADMIN',
                'WAREHOUSE_STAFF',
                'PURCHASING_STAFF'
            )
            """)
    public PurchaseRequestResponse getById(Long id) {

        PurchaseRequest purchaseRequest =
                getPurchaseRequest(id);

        validateViewAccess(purchaseRequest);

        return purchaseRequestMapper.toResponse(
                purchaseRequest
        );
    }

    @Transactional(readOnly = true)
    @PreAuthorize("""
            hasAnyRole(
                'ADMIN',
                'WAREHOUSE_STAFF',
                'PURCHASING_STAFF'
            )
            """)
    public List<PurchaseRequestResponse> getAll() {

        Users currentUser = getCurrentUser();

        List<PurchaseRequest> purchaseRequests;

        if (currentUser.getRole() == Role.WAREHOUSE_STAFF) {

            if (currentUser.getWarehouse() == null) {

                throw new AccessDeniedException(
                        "Warehouse staff has no assigned warehouse"
                );
            }

            Long warehouseId =
                    currentUser
                            .getWarehouse()
                            .getWarehouseId();

            purchaseRequests =
                    purchaseRequestRepository
                            .findByWarehouseWarehouseId(
                                    warehouseId
                            );

        } else {

            purchaseRequests =
                    purchaseRequestRepository.findAll();
        }

        return purchaseRequests
                .stream()
                .map(purchaseRequestMapper::toResponse)
                .toList();
    }

    @PreAuthorize("""
            hasAnyRole(
                'ADMIN',
                'WAREHOUSE_STAFF',
                'PURCHASING_STAFF'
            )
            """)
    public PurchaseRequestResponse update(
            Long id,
            UpdatePurchaseRequestRequest request
    ) {

        PurchaseRequest purchaseRequest =
                getPurchaseRequest(id);

        checkCanEdit(purchaseRequest);

        if (purchaseRequest.getStatus()
                != PurchaseRequestStatus.DRAFT) {

            throw new IllegalStateException(
                    "Only draft purchase requests can be updated"
            );
        }

        Users currentUser = getCurrentUser();

        Warehouse warehouse =
                findWarehouse(request.warehouseId());

        validateWarehouseAccess(
                currentUser,
                warehouse
        );

        Location location =
                findLocation(
                        request.locationId(),
                        warehouse
                );

        purchaseRequest.update(
                request.department(),
                request.requiredDate(),
                request.reason(),
                request.notes(),
                warehouse,
                location
        );

        if (request.lines() != null) {

            purchaseRequestLineRepository
                    .deleteAll(
                            purchaseRequestLineRepository
                                    .findByPurchaseRequestId(id)
                    );

            purchaseRequest.getLines().clear();

            saveLines(
                    purchaseRequest,
                    request.lines()
            );
        }

        return purchaseRequestMapper.toResponse(
                purchaseRequest
        );
    }

    @PreAuthorize("""
            hasAnyRole(
                'ADMIN',
                'WAREHOUSE_STAFF',
                'PURCHASING_STAFF'
            )
            """)
    public PurchaseRequestResponse submit(Long id) {

        PurchaseRequest purchaseRequest =
                getPurchaseRequest(id);

        checkCanEdit(purchaseRequest);

        if (purchaseRequest.getStatus()
                != PurchaseRequestStatus.DRAFT) {

            throw new IllegalStateException(
                    "Only draft purchase requests can be submitted"
            );
        }

        if (purchaseRequest.getLines().isEmpty()) {

            throw new IllegalStateException(
                    "Purchase request must contain at least one line"
            );
        }

        purchaseRequest.submit();

        return purchaseRequestMapper.toResponse(
                purchaseRequest
        );
    }

    @PreAuthorize("""
            hasAnyRole(
                'ADMIN',
                'PURCHASING_STAFF'
            )
            """)
    public PurchaseRequestResponse approve(Long id) {

        PurchaseRequest purchaseRequest =
                getPurchaseRequest(id);

        if (purchaseRequest.getStatus()
                != PurchaseRequestStatus.PENDING_APPROVAL) {

            throw new IllegalStateException(
                    "Only pending purchase requests can be approved"
            );
        }

        purchaseRequest.approve();

        return purchaseRequestMapper.toResponse(
                purchaseRequest
        );
    }

    @PreAuthorize("""
            hasAnyRole(
                'ADMIN',
                'PURCHASING_STAFF'
            )
            """)
    public PurchaseRequestResponse reject(Long id) {

        PurchaseRequest purchaseRequest =
                getPurchaseRequest(id);

        if (purchaseRequest.getStatus()
                != PurchaseRequestStatus.PENDING_APPROVAL) {

            throw new IllegalStateException(
                    "Only pending purchase requests can be rejected"
            );
        }

        purchaseRequest.reject();

        return purchaseRequestMapper.toResponse(
                purchaseRequest
        );
    }

    @PreAuthorize("""
            hasAnyRole(
                'ADMIN',
                'PURCHASING_STAFF'
            )
            """)
    public PurchaseRequestResponse process(Long id) {

        PurchaseRequest purchaseRequest =
                getPurchaseRequest(id);

        if (purchaseRequest.getStatus()
                != PurchaseRequestStatus.APPROVED) {

            throw new IllegalStateException(
                    "Only approved purchase requests can be processed"
            );
        }

        purchaseRequest.process();

        return purchaseRequestMapper.toResponse(
                purchaseRequest
        );
    }

    @PreAuthorize("""
            hasAnyRole(
                'ADMIN',
                'PURCHASING_STAFF'
            )
            """)
    public PurchaseRequestResponse complete(Long id) {

        PurchaseRequest purchaseRequest =
                getPurchaseRequest(id);

        if (purchaseRequest.getStatus()
                != PurchaseRequestStatus.PROCESSING) {

            throw new IllegalStateException(
                    "Only processing purchase requests can be completed"
            );
        }

        purchaseRequest.complete();

        return purchaseRequestMapper.toResponse(
                purchaseRequest
        );
    }

    private void saveLines(
            PurchaseRequest purchaseRequest,
            List<PurchaseRequestLineRequest> requests
    ) {

        List<PurchaseRequestLine> lines =
                requests.stream()
                        .map(request ->
                                createLine(
                                        purchaseRequest,
                                        request
                                )
                        )
                        .toList();

        purchaseRequestLineRepository.saveAll(lines);

        purchaseRequest.getLines().addAll(lines);
    }

    private PurchaseRequestLine createLine(
            PurchaseRequest purchaseRequest,
            PurchaseRequestLineRequest request
    ) {

        if (request.productId() == null &&
                (request.description() == null ||
                        request.description().isBlank())) {

            throw new IllegalArgumentException(
                    "Each purchase request line must have "
                            + "a product or description"
            );
        }

        Product product = null;

        if (request.productId() != null) {

            product =
                    productsRepository.findById(
                            request.productId()
                    ).orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Product not found: "
                                            + request.productId()
                            )
                    );
        }

        return new PurchaseRequestLine(
                purchaseRequest,
                product,
                request.description(),
                request.quantity(),
                request.unit(),
                request.requiredDate(),
                request.notes()
        );
    }

    private PurchaseRequest getPurchaseRequest(Long id) {

        return purchaseRequestRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Purchase request not found: " + id
                        )
                );
    }

    private Users getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new AccessDeniedException(
                    "Authentication is required"
            );
        }

        String username =
                authentication.getName();

        return usersRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Current user not found"
                        )
                );
    }

    private void checkCanEdit(
            PurchaseRequest purchaseRequest
    ) {

        Users currentUser = getCurrentUser();

        boolean isAdmin =
                currentUser.getRole() == Role.ADMIN;

        boolean isOwner =
                Objects.equals(
                        purchaseRequest
                                .getRequester()
                                .getUserId(),
                        currentUser.getUserId()
                );

        if (!isAdmin && !isOwner) {

            throw new AccessDeniedException(
                    "You can only modify your own purchase requests"
            );
        }

        validateViewAccess(purchaseRequest);
    }

    private void validateViewAccess(
            PurchaseRequest purchaseRequest
    ) {

        Users currentUser = getCurrentUser();

        if (currentUser.getRole() != Role.WAREHOUSE_STAFF) {
            return;
        }

        if (currentUser.getWarehouse() == null) {

            throw new AccessDeniedException(
                    "Warehouse staff has no assigned warehouse"
            );
        }

        if (!Objects.equals(
                currentUser
                        .getWarehouse()
                        .getWarehouseId(),
                purchaseRequest
                        .getWarehouse()
                        .getWarehouseId()
        )) {

            throw new AccessDeniedException(
                    "You can only access purchase requests "
                            + "for your assigned warehouse"
            );
        }
    }

    private void validateWarehouseAccess(
            Users user,
            Warehouse warehouse
    ) {

        if (warehouse.getStatus()
                != WarehouseStatus.ACTIVE) {

            throw new IllegalArgumentException(
                    "Warehouse must be active"
            );
        }

        if (user.getRole() == Role.WAREHOUSE_STAFF) {

            if (user.getWarehouse() == null) {

                throw new AccessDeniedException(
                        "Warehouse staff has no assigned warehouse"
                );
            }

            if (!Objects.equals(
                    user.getWarehouse().getWarehouseId(),
                    warehouse.getWarehouseId()
            )) {

                throw new AccessDeniedException(
                        "You can only create purchase requests "
                                + "for your assigned warehouse"
                );
            }
        }
    }

    private Warehouse findWarehouse(Long id) {

        return warehouseRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Warehouse not found: " + id
                        )
                );
    }

    private Location findLocation(
            Long locationId,
            Warehouse warehouse
    ) {

        if (locationId == null) {
            return null;
        }

        Location location =
                locationRepository.findById(locationId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Location not found: "
                                                + locationId
                                )
                        );

        if (!Objects.equals(
                location.getWarehouse().getWarehouseId(),
                warehouse.getWarehouseId()
        )) {

            throw new IllegalArgumentException(
                    "Location does not belong to the selected warehouse"
            );
        }

        if (location.getStatus()
                != LocationStatus.ACTIVE) {

            throw new IllegalArgumentException(
                    "Location must be active"
            );
        }

        return location;
    }

    private String generateRequestNo() {

        return "PR-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }
}