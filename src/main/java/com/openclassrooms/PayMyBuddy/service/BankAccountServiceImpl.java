package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dao.BankAccountRepository;
import com.openclassrooms.PayMyBuddy.model.BankAccount;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public Optional<BankAccount> findById(Long id) {
        return bankAccountRepository.findById(id);
    }

    @Override
    public BankAccount add(BankAccount bankAccount, Long userId) {
        Optional<User> owner = userService.findById(userId);
        bankAccount.setOwner(owner
                .stream()
                .filter(p -> Objects.equals(p.getId(), userId))
                .findFirst()
                .orElse(null));

        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public void delete(BankAccount bankAccount) {
        bankAccountRepository.delete(bankAccount);
    }
}
