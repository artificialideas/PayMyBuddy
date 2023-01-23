package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dto.ExternalTransactionDTO;

import java.util.List;

public interface ExternalTransferService {
    List<ExternalTransactionDTO> findExternalTransactionByUserEmail(String email);
}
