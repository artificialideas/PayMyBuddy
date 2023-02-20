package com.openclassrooms.PayMyBuddy.dto;

import lombok.Data;

@Data
public class ExternalTransferDTO {
    private String iban;
    private String description;
    private String amount;
}