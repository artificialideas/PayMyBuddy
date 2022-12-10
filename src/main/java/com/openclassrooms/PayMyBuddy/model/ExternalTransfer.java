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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ExternalTransfers")
public class ExternalTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_external")
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_receiver_bank",
            referencedColumnName="id_bank",
            unique = true,
            nullable = false)
    private BankAccount bankAccount;

    @ManyToOne(optional = false)
    @JoinColumn(
            name="id_transfer_table",
            nullable = false)
    private Transfer transferId;
}
