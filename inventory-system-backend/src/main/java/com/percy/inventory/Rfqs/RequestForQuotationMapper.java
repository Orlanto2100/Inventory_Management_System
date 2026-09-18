package com.percy.inventory.Rfqs;

import com.percy.inventory.Products.Product;
import com.percy.inventory.Rfqs.dto.CreateRfqRequest;
import com.percy.inventory.Rfqs.dto.RfqLineResponse;
import com.percy.inventory.Rfqs.dto.RfqResponse;
import com.percy.inventory.Vendor.Vendor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestForQuotationMapper {

    public RequestForQuotation toEntity(
            CreateRfqRequest request,
            String rfqNumber,
            List<Vendor> vendors,
            List<RequestForQuotationLine> lines
    ) {
        RequestForQuotation rfq = new RequestForQuotation(
                rfqNumber,
                request.title(),
                java.time.LocalDateTime.now(),
                request.responseDeadline(),
                request.description(),
                RfqStatus.DRAFT
        );

        vendors.forEach(rfq::addVendor);
        lines.forEach(rfq::addLine);

        return rfq;
    }

    public RfqResponse toResponse(RequestForQuotation rfq) {
        return new RfqResponse(
                rfq.getId(),
                rfq.getRfqNumber(),
                rfq.getTitle(),
                rfq.getRequestDate(),
                rfq.getResponseDeadline(),
                rfq.getDescription(),
                rfq.getStatus(),
                rfq.getVendors()
                        .stream()
                        .map(Vendor::getId)
                        .toList(),
                rfq.getVendors().size(),
                rfq.getLines().size(),
                rfq.getLines()
                        .stream()
                        .map(this::toLineResponse)
                        .toList()
        );
    }

    public RfqLineResponse toLineResponse(RequestForQuotationLine line) {
        Product product = line.getProduct();

        return new RfqLineResponse(
                line.getId(),
                product.getProductId(),
                product.getProductName(),
                line.getQuantity(),
                line.getNotes()
        );
    }
}