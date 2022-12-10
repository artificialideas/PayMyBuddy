package com.openclassrooms.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "BankAccounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bank")
    private long id;

    @Column(name = "credentials", nullable = false)
    private String credentials;

    @Column(name = "iban", nullable = false)
    private String iban;

    @Column(name = "swift", nullable = false)
    private String swift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_owner",
            referencedColumnName="id_user",
            unique = true,
            nullable = false)
    private User owner;
}
