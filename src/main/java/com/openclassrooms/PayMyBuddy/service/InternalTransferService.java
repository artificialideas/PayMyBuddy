package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dto.InternalTransactionDTO;

import java.util.List;

public interface InternalTransferService {
    List<InternalTransactionDTO> findInternalTransactionByUserEmail(String email);
}
