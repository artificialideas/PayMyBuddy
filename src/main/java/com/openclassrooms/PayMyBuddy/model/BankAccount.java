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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_owner",
            referencedColumnName="id_user",
            unique = true,
            nullable = false)
    private User owner;

    @OneToMany(
            mappedBy = "bankAccount",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<ExternalTransfer> externalTransfers = new ArrayList<>();

    /**
     *  Helper methods
     **/
    /* @ManyToOne -> owner */
    public User getUser() {
        return owner;
    }
    public void setUser(User owner) {
        this.owner = owner;
    }

    /* @OneToMany -> bankAccount */
    public List<ExternalTransfer> getExternalTransfer() {
        return externalTransfers;
    }
    public void setExternalTransfer(List<ExternalTransfer> externalTransfers) {
        this.externalTransfers = externalTransfers;
    }
    public void addExternalTransfer(ExternalTransfer externalTransfer) {
        externalTransfers.add(externalTransfer);
        externalTransfer.setBankAccount(this);
    }
    public void removeExternalTransfer(ExternalTransfer externalTransfer) {
        externalTransfers.remove(externalTransfer);
        externalTransfer.setBankAccount(null);
    }
}
