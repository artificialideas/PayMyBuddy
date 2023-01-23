package com.openclassrooms.PayMyBuddy.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExternalTransactionDTO {
    private String iban;
    private String description;
    private BigDecimal amount;
}