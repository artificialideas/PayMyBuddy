package com.openclassrooms.PayMyBuddy.dao;

import com.openclassrooms.PayMyBuddy.model.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
}
