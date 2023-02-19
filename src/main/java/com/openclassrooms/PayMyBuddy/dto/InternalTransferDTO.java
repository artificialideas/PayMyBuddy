package com.openclassrooms.PayMyBuddy.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

@Data
public class InternalTransferDTO {
    private EmitterDTO emitter;
    private ContactDTO receiver;
    private BigDecimal amount;
    private String description;

    public String getAmount() {
        Currency currency = Currency.getInstance("EUR");
        return amount.setScale(2, RoundingMode.HALF_EVEN) + currency.getSymbol();
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}