package com.openclassrooms.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name="ExternalTransfer")
public class ExternalTransfer extends Transfer {
    private BankAccount bankAccount;
}
