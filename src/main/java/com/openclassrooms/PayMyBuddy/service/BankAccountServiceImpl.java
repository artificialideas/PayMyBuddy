package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dao.BankAccountRepository;
import com.openclassrooms.PayMyBuddy.model.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Autowired
    private UserService userService;

    @Override
    public Optional<BankAccount> findBankAccountById(Long id) {
        return bankAccountRepository.findById(id);
    }

    @Override
    public BankAccount add(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public void deleteById(Long id) {
        bankAccountRepository.deleteById(id);
    }
}
