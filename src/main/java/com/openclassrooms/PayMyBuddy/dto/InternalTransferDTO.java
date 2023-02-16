package com.openclassrooms.PayMyBuddy.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InternalTransferDTO {
    private EmitterDTO emitter;
    private ContactDTO receiver;
    private BigDecimal amount;
    private String description;
}