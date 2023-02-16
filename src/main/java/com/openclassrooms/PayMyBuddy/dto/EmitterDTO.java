package com.openclassrooms.PayMyBuddy.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmitterDTO {
    private Long id;
    private BigDecimal savings;
}
