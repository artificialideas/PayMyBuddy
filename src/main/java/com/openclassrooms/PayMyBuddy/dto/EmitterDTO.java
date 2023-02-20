package com.openclassrooms.PayMyBuddy.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

@Data
public class EmitterDTO {
    private Long id;
    private BigDecimal savings;

    public String getSavings() {
        Currency currency = Currency.getInstance("EUR");
        return savings.setScale(2, RoundingMode.HALF_EVEN) + currency.getSymbol();
    }
    public void setSavings(BigDecimal savings) {
        this.savings = savings;
    }
}
