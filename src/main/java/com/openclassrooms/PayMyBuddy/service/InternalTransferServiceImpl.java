package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dao.InternalTransferRepository;
import com.openclassrooms.PayMyBuddy.dto.ContactDTO;
import com.openclassrooms.PayMyBuddy.dto.EmitterDTO;
import com.openclassrooms.PayMyBuddy.dto.InternalTransferDTO;
import com.openclassrooms.PayMyBuddy.model.InternalTransfer;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InternalTransferServiceImpl implements InternalTransferService {
    @Autowired
    private InternalTransferRepository internalTransferRepository;
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
        Optional<User> emitter = userService.findById(id);
        EmitterDTO emitterDTO = new EmitterDTO();
            emitterDTO.setId(emitter.map(User::getId).orElse(null));
            emitterDTO.setSavings(emitter.map(User::getSavings).orElse(null));

        // List all internal transfers from our emitter (user)
        List<InternalTransfer> internalTransfers = userService.findInternalTransferByUserId(id);
        // For each transfer, fill the InternalTransferDTO object
        for (InternalTransfer it : internalTransfers) {
            Optional<User> receiver = userService.findById(it.getReceiver().getId());
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

    @Override
    public InternalTransfer add(InternalTransferDTO internalTransferDTO, Long emitterId) {
        InternalTransfer internalTransfer = new InternalTransfer();

        Optional<User> emitter = userService.findById(emitterId);
        Long receiverId = userService.findUserByEmail(internalTransferDTO.getReceiver().getEmail()).getId();
        Optional<User> receiver = userService.findById(receiverId);

        if (emitter.isPresent() && receiver.isPresent()) {
            float emitterSavedMoney = emitter.get().getSavings().setScale(2, RoundingMode.HALF_EVEN).floatValue();
            float receiverSavedMoney = receiver.get().getSavings().setScale(2, RoundingMode.HALF_EVEN).floatValue();
            float enteredAmount = Float.parseFloat(internalTransferDTO.getAmount());

            // If savedMoney >= enteredAmount
            if (emitterSavedMoney >= enteredAmount) {
                // Save transfer
                internalTransfer.setEmitter(emitter.get());
                internalTransfer.setReceiver(receiver.get());
                internalTransfer.setAmount(BigDecimal.valueOf(enteredAmount));
                internalTransfer.setDescription(internalTransferDTO.getDescription());
                internalTransferRepository.save(internalTransfer);

                // Save new available amount
                    // Subtract 0.5% fee from emitter funds
                float fee = (float) (enteredAmount * 0.05);
                float availableMoney = emitterSavedMoney - (fee + enteredAmount);
                emitter.get().setSavings(BigDecimal.valueOf(availableMoney));
                userService.save(emitter.get());
                    // Add sent money to receiver funds
                receiver.get().setSavings(BigDecimal.valueOf(receiverSavedMoney + enteredAmount));
                userService.save(receiver.get());
            } else
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The entered amount is higher than the saved money");
        } else if (emitter.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, emitter.get().getEmail() + " doesn't exist");
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, receiver.get().getEmail() + " doesn't exist");

        return internalTransfer;
    }

    public Page<InternalTransfer> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<InternalTransfer> list;

        List<InternalTransfer> internalTransfers = new ArrayList<>();
        internalTransferRepository.findAll().iterator().forEachRemaining(internalTransfers::add);

        if (internalTransfers.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, internalTransfers.size());
            list = internalTransfers.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), internalTransfers.size());
    }
}
