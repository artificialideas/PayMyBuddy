package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dao.InternalTransferRepository;
import com.openclassrooms.PayMyBuddy.dto.ContactDTO;
import com.openclassrooms.PayMyBuddy.dto.InternalTransactionDTO;
import com.openclassrooms.PayMyBuddy.model.InternalTransfer;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InternalTransferServiceImpl implements InternalTransferService {
    @Autowired
    private InternalTransferRepository internalTransferRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<InternalTransactionDTO> findInternalTransactionByUserEmail(String email) {
        List<InternalTransactionDTO> internalTransactionDTOS = new ArrayList<>();

        User emitter = userService.findUserByEmail(email);
        List<ContactDTO> contacts = userService.findAllContactsByUserEmail(email);
        List<InternalTransfer> internalTransferResource = new ArrayList<>(emitter.getInternalTransfers());
        for (InternalTransfer internalTransfer : internalTransferResource) {
            InternalTransactionDTO internalTransactionDTO = new InternalTransactionDTO();
            internalTransactionDTO.setReceiver(contacts);
            internalTransactionDTO.setDescription(internalTransfer.getDescription());
            internalTransactionDTO.setAmount(internalTransfer.getAmount());
            internalTransactionDTOS.add(internalTransactionDTO);
        }

        return internalTransactionDTOS;
    }
}
