package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dao.BankAccountRepository;
import com.openclassrooms.PayMyBuddy.model.BankAccount;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank account not found with iban " + iban);
        }

        return bankAccount;
    }

    @Override
    public BankAccount add(BankAccount bankAccount, Long userId) {
        // Check if the entered Iban already exists
        if (findBankAccountByIban(bankAccount.getIban()) == null) {
            Optional<User> owner = userService.findById(userId);
            if (owner.isPresent()) {
                // Check if the entered Iban already exists in user's list
                if (!Objects.equals(owner.get().getBankAccounts().listIterator().next().getIban(), bankAccount.getIban())) {
                    bankAccount.setOwner(owner.get());
                } else
                    throw new ResponseStatusException(HttpStatus.IM_USED, bankAccount.getIban() + " already exists");
            } else
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, owner.get().getEmail() + " doesn't exist");
        } else
            throw new ResponseStatusException(HttpStatus.IM_USED, bankAccount.getIban() + " already exists");

        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public void delete(BankAccount bankAccount) {
        bankAccountRepository.delete(bankAccount);
    }
}
