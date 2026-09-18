package com.percy.inventory.Rfqs.dto;

import jakarta.validation.constraints.NotBlank;

public record RfqEmailRequest(

        @NotBlank
        String emailSubject,

        @NotBlank
        String emailMessage
) {
}