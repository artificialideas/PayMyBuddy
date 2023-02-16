package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dto.ExternalTransferDTO;

import java.util.List;

public interface ExternalTransferService {
    List<ExternalTransferDTO> findExternalTransactionByUserEmail(String email);
}
