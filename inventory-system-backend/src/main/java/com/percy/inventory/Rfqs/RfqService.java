package com.percy.inventory.Rfqs;

import com.percy.inventory.Products.Product;
import com.percy.inventory.Products.ProductRepository;
import com.percy.inventory.Rfqs.dto.*;
import com.percy.inventory.Vendor.Vendor;
import com.percy.inventory.Vendor.VendorRepository;
import com.percy.inventory.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RfqService {

    private final RequestForQuotationMapper rfqMapper;
    private final RequestForQuotationRepository rfqRepository;
    private final VendorRepository vendorRepository;
    private final ProductRepository productRepository;
    private final EmailService emailService;

    public RfqResponse createRfq(CreateRfqRequest request) {

        List<Vendor> vendors =
                vendorRepository.findAllById(request.vendorIds());

        if (vendors.size() != request.vendorIds().size()) {
            throw new ResourceNotFoundException(
                    "One or more vendors not found"
            );
        }

        RequestForQuotation rfq = rfqMapper.toEntity(
                request,
                generateRfqNumber(),
                vendors,
                List.of()
        );

        for (RfqLineRequest item : request.items()) {

            Product product =
                    productRepository.findById(item.productId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Product not found"
                                    )
                            );

            RequestForQuotationLine line =
                    new RequestForQuotationLine(
                            rfq,
                            product,
                            item.quantity(),
                            item.notes()
                    );

            rfq.addLine(line);
        }

        RequestForQuotation savedRfq =
                rfqRepository.save(rfq);

        return rfqMapper.toResponse(savedRfq);
    }

    public RfqResponse getRfqById(Long id) {

        RequestForQuotation rfq =
                rfqRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "RFQ not found"
                                )
                        );

        return rfqMapper.toResponse(rfq);
    }

    public Page<RfqResponse> listRfqs(
            String search,
            RfqStatus status,
            Long vendorId,
            LocalDate responseDeadline,
            Pageable pageable
    ) {

        Specification<RequestForQuotation> specification =
                Specification
                        .where(RfqSpecification.search(search))
                        .and(RfqSpecification.hasStatus(status))
                        .and(RfqSpecification.hasVendor(vendorId))
                        .and(RfqSpecification.responseDeadline(responseDeadline));

        return rfqRepository
                .findAll(specification, pageable)
                .map(rfqMapper::toResponse);
    }

    public RfqResponse updateRfq(
            Long rfqId,
            UpdateRfqRequest request
    ) {

        RequestForQuotation rfq =
                rfqRepository.findById(rfqId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "RFQ not found"
                                )
                        );

        rfq.update(
                request.title(),
                request.responseDeadline(),
                request.description()
        );

        if (request.vendorIds() != null) {

            List<Vendor> vendors =
                    vendorRepository.findAllById(request.vendorIds());

            if (vendors.size() != request.vendorIds().size()) {
                throw new ResourceNotFoundException(
                        "One or more vendors not found"
                );
            }

            rfq.getVendors().clear();
            vendors.forEach(rfq::addVendor);
        }

        if (request.items() != null) {

            rfq.getLines().clear();

            for (RfqLineRequest item : request.items()) {

                Product product =
                        productRepository.findById(item.productId())
                                .orElseThrow(() ->
                                        new ResourceNotFoundException(
                                                "Product not found"
                                        )
                                );

                RequestForQuotationLine line =
                        new RequestForQuotationLine(
                                rfq,
                                product,
                                item.quantity(),
                                item.notes()
                        );

                rfq.addLine(line);
            }
        }

        RequestForQuotation savedRfq =
                rfqRepository.save(rfq);

        return rfqMapper.toResponse(savedRfq);
    }

    public RfqEmailResponse generateEmailPreview(Long rfqId) {

        RequestForQuotation rfq =
                rfqRepository.findById(rfqId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "RFQ not found"
                                )
                        );

        String subject =
                "Request for Quotation - " + rfq.getRfqNumber();

        StringBuilder message = new StringBuilder();

        message.append("Dear Vendor,\n\n");

        message.append(
                "We would like to request a quotation for the following items:\n\n"
        );

        message.append("RFQ Number: ")
                .append(rfq.getRfqNumber())
                .append("\n");

        message.append("Title: ")
                .append(rfq.getTitle())
                .append("\n");

        message.append("Response Deadline: ")
                .append(rfq.getResponseDeadline())
                .append("\n\n");

        message.append("Requested Items:\n\n");

        for (RequestForQuotationLine line : rfq.getLines()) {

            message.append("- ")
                    .append(line.getProduct().getProductName())
                    .append(" — Quantity: ")
                    .append(line.getQuantity());

            if (line.getNotes() != null &&
                    !line.getNotes().isBlank()) {

                message.append(" — ")
                        .append(line.getNotes());
            }

            message.append("\n");
        }

        if (rfq.getDescription() != null &&
                !rfq.getDescription().isBlank()) {

            message.append("\nAdditional Information:\n")
                    .append(rfq.getDescription())
                    .append("\n");
        }

        message.append("\n")
                .append("Please provide your quotation before the response deadline.\n\n");

        message.append("Thank you.");

        return new RfqEmailResponse(
                subject,
                message.toString()
        );
    }

    public void sendRfq(
            Long rfqId,
            RfqEmailRequest request
    ) {

        RequestForQuotation rfq =
                rfqRepository.findById(rfqId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "RFQ not found"
                                )
                        );

        for (Vendor vendor : rfq.getVendors()) {

            emailService.sendEmail(
                    vendor.getEmail(),
                    request.emailSubject(),
                    request.emailMessage()
            );
        }

        rfq.changeStatus(RfqStatus.SENT);

        rfqRepository.save(rfq);
    }

    public void closeRfq(Long rfqId) {

        RequestForQuotation rfq =
                rfqRepository.findById(rfqId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "RFQ not found"
                                )
                        );

        rfq.changeStatus(RfqStatus.CLOSED);

        rfqRepository.save(rfq);
    }

    public void deleteRfq(Long rfqId) {

        RequestForQuotation rfq =
                rfqRepository.findById(rfqId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "RFQ not found"
                                )
                        );

        rfqRepository.delete(rfq);
    }

    private String generateRfqNumber() {

        return "RFQ-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }
}