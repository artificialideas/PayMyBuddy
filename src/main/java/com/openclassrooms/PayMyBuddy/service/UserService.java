package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dto.BankAccountDTO;
import com.openclassrooms.PayMyBuddy.dto.ContactDTO;
import com.openclassrooms.PayMyBuddy.dto.UserDetailsDTO;
import com.openclassrooms.PayMyBuddy.model.ExternalTransfer;
import com.openclassrooms.PayMyBuddy.model.InternalTransfer;
import com.openclassrooms.PayMyBuddy.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Iterable<User> findAll();

    Optional<User> findById(Long id);

    User findUserByEmailAndPassword(String email);

    UserDetailsDTO findUserByEmail(String email);

    List<ContactDTO> findContactsByUserEmail(String email);

    List<BankAccountDTO> findBankAccountsByUserId(Long id);

    List<InternalTransfer> findInternalTransferByUserId(Long id);

    List<ExternalTransfer> findExternalTransferByUserId(Long id);

    void update(User user, Long id);

    User addContact(Long userId, Long contactId);

    void deleteContact(Long userId, Long contactId);
}
