package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dao.InternalTransferRepository;
import com.openclassrooms.PayMyBuddy.dto.ContactDTO;
import com.openclassrooms.PayMyBuddy.dto.EmitterDTO;
import com.openclassrooms.PayMyBuddy.dto.InternalTransferDTO;
import com.openclassrooms.PayMyBuddy.model.InternalTransfer;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Service
public class InternalTransferServiceImpl implements InternalTransferService {
    @Autowired
    private InternalTransferRepository internalTransferRepository;

    public InternalTransferServiceImpl(InternalTransferRepository internalTransferRepository) {
        this.internalTransferRepository = internalTransferRepository;
    }

    @Autowired
    private UserService userService;

    @Override
    public Optional<InternalTransfer> findInternalTransferById(Long id) {
        return internalTransferRepository.findById(id);
    }

    @Override
    public List<InternalTransferDTO> findInternalTransferByUserId(Long id) {
        List<InternalTransferDTO> allInternalTransfers = new ArrayList<>();
        Currency currency = Currency.getInstance("EUR");

        // There is only one emitter
        Optional<User> emitter = userService.findUserById(id);
        EmitterDTO emitterDTO = new EmitterDTO();
            emitterDTO.setId(emitter.map(User::getId).orElse(null));
            emitterDTO.setSavings(emitter.map(User::getSavings).orElse(null));

        // List all internal transfers from our emitter (user)
        List<InternalTransfer> internalTransfers = userService.findInternalTransferByUserId(id);
        // For each transfer, fill the InternalTransferDTO object
        for (InternalTransfer it : internalTransfers) {
            Optional<User> receiver = userService.findUserById(it.getReceiver().getId());
            ContactDTO receiverDTO = new ContactDTO();
                receiverDTO.setFirstName(receiver.map(User::getFirstName).orElse(null));
                receiverDTO.setLastName(receiver.map(User::getLastName).orElse(null));
                receiverDTO.setEmail(receiver.map(User::getEmail).orElse(null));

            Optional<InternalTransfer> internalTransfer = findInternalTransferById(it.getId());
            BigDecimal formattedAmount = internalTransfer.map(InternalTransfer::getAmount).orElse(null);
            assert formattedAmount != null;

            InternalTransferDTO internalTransferDTO = new InternalTransferDTO();
                internalTransferDTO.setEmitter(emitterDTO);
                internalTransferDTO.setReceiver(receiverDTO);
                internalTransferDTO.setAmount(formattedAmount.setScale(2, RoundingMode.HALF_EVEN) + currency.getSymbol());
                internalTransferDTO.setDescription(internalTransfer.map(InternalTransfer::getDescription).orElse(null));
            allInternalTransfers.add(internalTransferDTO);
        }

        return allInternalTransfers;
    }
}
