package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.model.BankAccount;

import java.util.Optional;

public interface BankAccountService {
    Optional<BankAccount> findById(Long id);

    BankAccount add(BankAccount bankAccount, Long userId);

    void delete(BankAccount bankAccount);
}
