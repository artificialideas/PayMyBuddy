package com.openclassrooms.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "BankAccounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bank")
    private int id;

    @Column(name = "credentials", nullable = false)
    private String credentials;

    @Column(name = "iban", nullable = false)
    private String iban;

    @Column(name = "swift", nullable = false)
    private String swift;

    @OneToOne
    @JoinColumn(name = "id_owner", nullable = false)
    private User owner;
}
