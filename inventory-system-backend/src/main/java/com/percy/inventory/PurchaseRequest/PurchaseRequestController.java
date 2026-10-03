package com.percy.inventory.PurchaseRequest;

import com.percy.inventory.PurchaseRequest.dto.CreatePurchaseRequestRequest;
import com.percy.inventory.PurchaseRequest.dto.PurchaseRequestResponse;
import com.percy.inventory.PurchaseRequest.dto.UpdatePurchaseRequestRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-requests")
@RequiredArgsConstructor
public class PurchaseRequestController {

    private final PurchaseRequestService purchaseRequestService;

    @PostMapping
    public ResponseEntity<PurchaseRequestResponse> create(
            @Valid @RequestBody CreatePurchaseRequestRequest request
    ) {

        PurchaseRequestResponse response =
                purchaseRequestService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseRequestResponse>> getAll() {

        return ResponseEntity.ok(
                purchaseRequestService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseRequestResponse> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                purchaseRequestService.getById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseRequestResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePurchaseRequestRequest request
    ) {

        return ResponseEntity.ok(
                purchaseRequestService.update(
                        id,
                        request
                )
        );
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<PurchaseRequestResponse> submit(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                purchaseRequestService.submit(id)
        );
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<PurchaseRequestResponse> approve(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                purchaseRequestService.approve(id)
        );
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<PurchaseRequestResponse> reject(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                purchaseRequestService.reject(id)
        );
    }

    @PostMapping("/{id}/process")
    public ResponseEntity<PurchaseRequestResponse> process(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                purchaseRequestService.process(id)
        );
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<PurchaseRequestResponse> complete(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                purchaseRequestService.complete(id)
        );
    }
}