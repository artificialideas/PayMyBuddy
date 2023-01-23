package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dao.ExternalTransferRepository;
import com.openclassrooms.PayMyBuddy.dto.ExternalTransactionDTO;
import com.openclassrooms.PayMyBuddy.model.ExternalTransfer;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExternalTransferServiceImpl implements ExternalTransferService {
    @Autowired
    private ExternalTransferRepository externalTransferRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<ExternalTransactionDTO> findExternalTransactionByUserEmail(String email) {
        List<ExternalTransactionDTO> externalTransactionDTOS = new ArrayList<>();

        User owner = userService.findUserByEmail(email);
        List<ExternalTransfer> externalTransferResource = new ArrayList<>(owner.getExternalTransfers());
        for (ExternalTransfer externalTransfer : externalTransferResource) {
            // Mask iban number for safety purpose
            String rawIban = externalTransfer.getBankAccount().getIban();
            String tmp = rawIban.substring(5);
            String maskedIban = rawIban.substring(0, 4) + tmp.replaceAll("\\w(?=\\w{4})", "*");

            ExternalTransactionDTO externalTransactionDTO = new ExternalTransactionDTO();
            externalTransactionDTO.setIban(maskedIban);
            externalTransactionDTO.setDescription(externalTransfer.getDescription());
            externalTransactionDTO.setAmount(externalTransfer.getAmount());
            externalTransactionDTOS.add(externalTransactionDTO);
        }

        return externalTransactionDTOS;
    }
}
