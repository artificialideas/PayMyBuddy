package com.openclassrooms.PayMyBuddy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "BankAccounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_bank")
    private long id;

    @Column(name = "credentials", nullable = false)
    private String credentials;

    @Column(name = "iban", unique = true, nullable = false)
    private String iban;

    @Column(name = "swift", nullable = false)
    private String swift;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "id_owner",
            unique = true,
            nullable = false)
    private User owner;

    @OneToOne(
            mappedBy = "bankAccount",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private ExternalTransfer externalTransfer;

    /**
     *  Helper methods
     **/
    /* @ManyToOne -> owner */
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /* @OneToOne -> bankAccount */
    public ExternalTransfer getExternalTransfer() {
        return externalTransfer;
    }
    public void setExternalTransfer(ExternalTransfer externalTransfer) {
        this.externalTransfer = externalTransfer;
    }
}
