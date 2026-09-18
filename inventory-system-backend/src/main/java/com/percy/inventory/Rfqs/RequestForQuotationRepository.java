package com.percy.inventory.Rfqs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RequestForQuotationRepository
        extends JpaRepository<RequestForQuotation, Long>,
        JpaSpecificationExecutor<RequestForQuotation> {
}