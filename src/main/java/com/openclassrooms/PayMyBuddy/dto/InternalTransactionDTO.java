package com.openclassrooms.PayMyBuddy.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class InternalTransactionDTO {
    private List<ContactDTO> receiver;
    private String description;
    private BigDecimal amount;
}