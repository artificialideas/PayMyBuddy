package com.openclassrooms.PayMyBuddy.dto;

import lombok.Data;

@Data
public class BankAccountDTO {
    private Long id;
    private String iban;
    private String swift;
}
