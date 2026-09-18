package com.percy.inventory.Rfqs;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RfqSpecification {

    public static Specification<RequestForQuotation> search(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isBlank()) {
                return null;
            }

            String value = "%" + search.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("rfqNumber")), value),
                    cb.like(cb.lower(root.get("title")), value),
                    cb.like(cb.lower(root.get("description")), value)
            );
        };
    }

    public static Specification<RequestForQuotation> hasStatus(
            RfqStatus status
    ) {
        return (root, query, cb) -> {
            if (status == null) {
                return null;
            }

            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<RequestForQuotation> hasVendor(
            Long vendorId
    ) {
        return (root, query, cb) -> {
            if (vendorId == null) {
                return null;
            }

            Join<RequestForQuotation, com.percy.inventory.Vendor.Vendor> vendor =
                    root.join("vendors");

            return cb.equal(vendor.get("id"), vendorId);
        };
    }

    public static Specification<RequestForQuotation> responseDeadline(
            LocalDate date
    ) {
        return (root, query, cb) -> {
            if (date == null) {
                return null;
            }

            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.plusDays(1).atStartOfDay();

            return cb.and(
                    cb.greaterThanOrEqualTo(
                            root.get("responseDeadline"),
                            start
                    ),
                    cb.lessThan(
                            root.get("responseDeadline"),
                            end
                    )
            );
        };
    }
}