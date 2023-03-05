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
    public Iterable<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return bankAccountRepository.findById(id);
    }

    @Override
    public BankAccount findBankAccountByIban(String iban) {
        Iterable<BankAccount> bankAccounts = findAll();
        BankAccount bankAccount = new BankAccount();

        for (BankAccount b : bankAccounts) {
            if (Objects.equals(b.getIban(), iban)) {
                bankAccount.setId(b.getId());
                bankAccount.setCredentials(b.getCredentials());
                bankAccount.setIban(b.getIban());
                bankAccount.setSwift(b.getSwift());
                bankAccount.setOwner(b.getOwner());
                break;
            }
        }

        if (bankAccount.getIban() == null) {
            throw new RuntimeException("Bank account not found with iban " + iban);
        }

        return bankAccount;
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
