package com.percy.inventory.Rfqs;

import com.percy.inventory.Rfqs.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/rfqs")
@RequiredArgsConstructor
public class RfqController {

    private final RfqService rfqService;

    @PostMapping
    public ResponseEntity<RfqResponse> createRfq(
            @Valid @RequestBody CreateRfqRequest request
    ) {
        RfqResponse response = rfqService.createRfq(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RfqResponse> getRfqById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                rfqService.getRfqById(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<RfqResponse>> listRfqs(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) RfqStatus status,
            @RequestParam(required = false) Long vendorId,
            @RequestParam(required = false) LocalDate responseDeadline,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                rfqService.listRfqs(
                        search,
                        status,
                        vendorId,
                        responseDeadline,
                        pageable
                )
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RfqResponse> updateRfq(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRfqRequest request
    ) {
        return ResponseEntity.ok(
                rfqService.updateRfq(id, request)
        );
    }

    @GetMapping("/{id}/email-preview")
    public ResponseEntity<RfqEmailResponse> getEmailPreview(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                rfqService.generateEmailPreview(id)
        );
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<Void> sendRfq(
            @PathVariable Long id,
            @Valid @RequestBody RfqEmailRequest request
    ) {
        rfqService.sendRfq(id, request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRfq(
            @PathVariable Long id
    ) {
        rfqService.deleteRfq(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<Void> closeRfq(
            @PathVariable Long id
    ) {
        rfqService.closeRfq(id);

        return ResponseEntity.noContent().build();
    }
}