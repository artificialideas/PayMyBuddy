package com.openclassrooms.PayMyBuddy.dto;

import lombok.Data;

@Data
public class InternalTransferDTO {
    private EmitterDTO emitter;
    private ContactDTO receiver;
    private String amount;
    private String description;
}