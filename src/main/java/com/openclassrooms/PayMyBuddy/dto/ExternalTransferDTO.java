package com.openclassrooms.PayMyBuddy.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExternalTransferDTO {
    private String iban;
    private String description;
    private BigDecimal amount;
}