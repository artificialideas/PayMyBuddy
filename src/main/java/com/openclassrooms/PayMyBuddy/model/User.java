package com.openclassrooms.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany
    @JoinTable(
        name="Contacts",
        joinColumns = @JoinColumn(
                name="id_user",
                referencedColumnName = "id_user"),
        inverseJoinColumns = @JoinColumn(
                name="id_friend",
                referencedColumnName = "id_user"))
    private List<User> contacts = new ArrayList<>();

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<BankAccount> bankAccounts = new ArrayList<>();

    @OneToMany(
            mappedBy = "emitter",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE},
            orphanRemoval = true)
    private List<InternalTransfer> internalTransfers = new ArrayList<>();

    @OneToOne(
            mappedBy = "receiver",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private InternalTransfer internalTransfer;

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
    public List<User> getContacts() {
        return contacts;
    }
    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }
    public void addContact(User contact) {
        contacts.add(contact);
    }
    public void removeContact(User contact) {
        contacts.remove(contact);
    }

    /* @OneToMany -> owner */
    public List<BankAccount> getBankAccount() {
        return bankAccounts;
    }
    public void setBankAccount(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }
    public void addBankAccount(BankAccount bankAccount) {
        bankAccounts.add(bankAccount);
    }
    public void removeBankAccount(BankAccount bankAccount) {
        bankAccounts.remove(bankAccount);
    }

    /* @OneToMany & @OneToOne -> emitter & receiver (InternalTransfer) */
    public List<InternalTransfer> getInternalTransfer() {
        return internalTransfers;
    }
    public void setInternalTransfer(List<InternalTransfer> internalTransfers) {
        this.internalTransfers = internalTransfers;
    }
    public void addInternalTransfer(InternalTransfer internalTransfer) {
        internalTransfers.add(internalTransfer);
    }
    public void removeInternalTransfer(InternalTransfer internalTransfer) {
        internalTransfers.remove(internalTransfer);
    }

    /* @OneToMany -> emitter (ExternalTransfer) */
    public List<ExternalTransfer> getExternalTransfer() {
        return externalTransfers;
    }
    public void setExternalTransfer(List<ExternalTransfer> externalTransfers) {
        this.externalTransfers = externalTransfers;
    }
    public void addExternalTransfer(ExternalTransfer externalTransfer) {
        externalTransfers.add(externalTransfer);
    }
    public void removeExternalTransfer(ExternalTransfer externalTransfer) {
        externalTransfers.remove(externalTransfer);
    }
}
