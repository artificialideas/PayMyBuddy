package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dao.ExternalTransferRepository;
import com.openclassrooms.PayMyBuddy.dto.EmitterDTO;
import com.openclassrooms.PayMyBuddy.dto.ExternalTransferDTO;
import com.openclassrooms.PayMyBuddy.model.ExternalTransfer;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Service
public class ExternalTransferServiceImpl implements ExternalTransferService {
    @Autowired
    private ExternalTransferRepository externalTransferRepository;

    public ExternalTransferServiceImpl(ExternalTransferRepository externalTransferRepository) {
        this.externalTransferRepository = externalTransferRepository;
    }

    @Autowired
    private UserService userService;

    @Override
    public Optional<ExternalTransfer> findExternalTransferById(Long id) {
        return externalTransferRepository.findById(id);
    }

    @Override
    public List<ExternalTransferDTO> findExternalTransactionByUserId(Long id) {
        List<ExternalTransferDTO> allExternalTransfers = new ArrayList<>();
        Currency currency = Currency.getInstance("EUR");

        // There is only one emitter
        Optional<User> emitter = userService.findById(id);
        EmitterDTO emitterDTO = new EmitterDTO();
            emitterDTO.setId(emitter.map(User::getId).orElse(null));
            emitterDTO.setSavings(emitter.map(User::getSavings).orElse(null));

        // List all external transfers from our emitter (user)
        List<ExternalTransfer> externalTransfers = userService.findExternalTransferByUserId(id);
        for (ExternalTransfer et : externalTransfers) {
            // Mask iban number for safety purpose
            String rawIban = et.getBankAccount().getIban();
            String tmp = rawIban.substring(5);
            String maskedIban = rawIban.substring(0, 4) + tmp.replaceAll("\\w(?=\\w{4})", "*");

            ExternalTransferDTO externalTransactionDTO = new ExternalTransferDTO();
                externalTransactionDTO.setIban(maskedIban);
                externalTransactionDTO.setDescription(et.getDescription());
            externalTransactionDTO.setAmount(et.getAmount().setScale(2, RoundingMode.HALF_EVEN) + currency.getSymbol());
            allExternalTransfers.add(externalTransactionDTO);
        }

        return allExternalTransfers;
    }
}
