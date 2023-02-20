package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dto.ExternalTransferDTO;
import com.openclassrooms.PayMyBuddy.model.ExternalTransfer;

import java.util.List;
import java.util.Optional;

public interface ExternalTransferService {
    Optional<ExternalTransfer> findExternalTransferById(Long id);

    List<ExternalTransferDTO> findExternalTransactionByUserId(Long id);
}
