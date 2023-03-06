package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dao.ExternalTransferRepository;
import com.openclassrooms.PayMyBuddy.dto.EmitterDTO;
import com.openclassrooms.PayMyBuddy.dto.ExternalTransferDTO;
import com.openclassrooms.PayMyBuddy.model.BankAccount;
import com.openclassrooms.PayMyBuddy.model.ExternalTransfer;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Service
public class ExternalTransferServiceImpl implements ExternalTransferService {
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private ExternalTransferRepository externalTransferRepository;

    @Override
    public Optional<ExternalTransfer> findById(Long id) {
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

    @Override
    public ExternalTransfer add(ExternalTransferDTO externalTransferDTO, Long emitterId) {
        // Check if there are enough money to withdraw
        Optional<User> emitter = userService.findById(emitterId);
        ExternalTransfer externalTransfer = new ExternalTransfer();

        if (emitter.isPresent()) {
            float savedMoney = emitter.get().getSavings().setScale(2, RoundingMode.HALF_EVEN).floatValue();
            float enteredAmount = Float.parseFloat(externalTransferDTO.getAmount());
            // If savedMoney >= enteredAmount
            if (savedMoney >= enteredAmount) {
                BankAccount bankAccount = bankAccountService.findBankAccountByIban(externalTransferDTO.getIban());
                // Save transfer
                externalTransfer.setEmitter(emitter.get());
                externalTransfer.setAmount(BigDecimal.valueOf(enteredAmount));
                externalTransfer.setDescription(externalTransferDTO.getDescription());
                externalTransfer.setBankAccount(bankAccount);
                externalTransferRepository.save(externalTransfer);

                // Save new available amount
                float availableMoney = savedMoney - enteredAmount;
                emitter.get().setSavings(BigDecimal.valueOf(availableMoney));
                userService.save(emitter.get());
            } else throw new RuntimeException("The entered amount is higher than the saved money");
        } else throw new RuntimeException(emitter.get().getEmail() + " doesn't exist");

        return externalTransfer;
    }

    public Page<ExternalTransfer> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ExternalTransfer> list;

        List<ExternalTransfer> externalTransfers = new ArrayList<>();
        externalTransferRepository.findAll().iterator().forEachRemaining(externalTransfers::add);

        if (externalTransfers.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, externalTransfers.size());
            list = externalTransfers.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), externalTransfers.size());
    }
}
