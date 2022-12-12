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
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @OneToMany(
            mappedBy = "emitter",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<Transfer> transfers = new ArrayList<>();
}
