package com.openclassrooms.PayMyBuddy.model;

import lombok.Data;

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

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "pass_word", nullable = false)
    private String password;

    @Column(name = "savings", nullable = false)
    private BigDecimal savings;

    @ManyToMany(fetch = FetchType.EAGER)
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
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    List<BankAccount> bankAccounts = new ArrayList<>();

//    @OneToMany(
//            mappedBy = "emitter",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            fetch = FetchType.EAGER)
//    private List<InternalTransfer> internalTransfers = new ArrayList<>();
//
//    @OneToMany(
//            mappedBy = "emitter",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            fetch = FetchType.EAGER)
//    private List<ExternalTransfer> externalTransfers = new ArrayList<>();

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
        contact.getContacts().add(this);
    }
    public void removeContact(User contact) {
        contacts.remove(contact);
        contact.getContacts().remove(this);
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
        bankAccount.setUser(this);
    }
    public void removeBankAccount(BankAccount bankAccount) {
        bankAccounts.remove(bankAccount);
        bankAccount.setUser(null);
    }
}
