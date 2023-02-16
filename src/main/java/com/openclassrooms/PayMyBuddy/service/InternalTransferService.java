package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dto.InternalTransferDTO;
import com.openclassrooms.PayMyBuddy.model.InternalTransfer;

import java.util.List;
import java.util.Optional;

public interface InternalTransferService {
    Optional<InternalTransfer> findInternalTransferById(Long id);

    List<InternalTransferDTO> findInternalTransferByUserId(Long id);
}
