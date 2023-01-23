package com.openclassrooms.PayMyBuddy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user")
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "pass_word", nullable = false)
    private String password;

    @Column(name = "savings", nullable = false)
    private BigDecimal savings;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name="Contacts",
        joinColumns = @JoinColumn(
                name="id_user"),
        inverseJoinColumns = @JoinColumn(
                name="id_friend"))
    private List<User> contacts = new ArrayList<>();

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @OneToMany(
            mappedBy = "emitter",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE},
            orphanRemoval = true)
    private List<InternalTransfer> internalTransfers = new ArrayList<>();

    /*@OneToOne(
            mappedBy = "receiver",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private InternalTransfer internalTransfer;*/

    @OneToMany(
            mappedBy = "emitter",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE},
            orphanRemoval = true)
    private List<ExternalTransfer> externalTransfers = new ArrayList<>();

    /**
     *  Helper methods
     **/
    /* @ManyToMany -> contacts */
    /*public List<User> getContacts() {
        return contacts;
    }
    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }*/
    public void addContact(User contact) {
        this.contacts.add(contact);
    }
    public void removeContact(User contact) {
        this.contacts.remove(contact);
    }

    /* @OneToMany -> owner */
    /*public List<BankAccount> getBankAccount() {
        return bankAccounts;
    }
    public void setBankAccount(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }*/
    public void addBankAccount(BankAccount bankAccount) {
        this.bankAccounts.add(bankAccount);
        bankAccount.setOwner(this);
    }
    public void removeBankAccount(BankAccount bankAccount) {
        this.bankAccounts.remove(bankAccount);
        bankAccount.setOwner(this);
    }

    /* @OneToMany & @OneToOne -> emitter & receiver (InternalTransfer) *//*
    /*public List<InternalTransfer> getInternalTransfer() {
        return internalTransfers;
    }
    public void setInternalTransfer(List<InternalTransfer> internalTransfers) {
        this.internalTransfers = internalTransfers;
    }*/
    public void addInternalTransfer(InternalTransfer internalTransfer) {
        this.internalTransfers.add(internalTransfer);
        internalTransfer.setEmitter(this);
        //internalTransfer.setReceiver(this);
    }
    public void removeInternalTransfer(InternalTransfer internalTransfer) {
        this.internalTransfers.remove(internalTransfer);
        internalTransfer.setEmitter(this);
        //internalTransfer.setReceiver(this);
    }

    /* @OneToMany -> emitter (ExternalTransfer) *//*
    /*public List<ExternalTransfer> getExternalTransfer() {
        return externalTransfers;
    }
    public void setExternalTransfer(List<ExternalTransfer> externalTransfers) {
        this.externalTransfers = externalTransfers;
    }*/
    public void addExternalTransfer(ExternalTransfer externalTransfer) {
        this.externalTransfers.add(externalTransfer);
        externalTransfer.setEmitter(this);
    }
    public void removeExternalTransfer(ExternalTransfer externalTransfer) {
        this.externalTransfers.remove(externalTransfer);
        externalTransfer.setEmitter(this);
    }
}
