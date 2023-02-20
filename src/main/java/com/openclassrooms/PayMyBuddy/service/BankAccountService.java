package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.model.BankAccount;

import java.util.Optional;

public interface BankAccountService {
    Optional<BankAccount> findBankAccountById(Long id);

    BankAccount add(BankAccount bankAccount);

    void deleteById(Long id);
}
