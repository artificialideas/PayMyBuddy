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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Transfers")
abstract class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transfer")
    private long id;

    @Column(name = "emission_date", nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_emitter_user",
            referencedColumnName="id_user",
            unique = true,
            nullable = false)
    private User emitter;

    @OneToMany(
            mappedBy = "transfer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<InternalTransfer> internalTransfers = new ArrayList<>();

    @OneToMany(
            mappedBy = "transfer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<ExternalTransfer> externalTransfers = new ArrayList<>();

    /**
     *  Helper methods
     **/
    public User getUser() {
        return emitter;
    }
    public void setUser(User emitter) {
        this.emitter = emitter;
    }

    public void addInternalTransfer(InternalTransfer internalTransfer) {
        internalTransfers.add(internalTransfer);
        internalTransfer.setTransfer(this);
    }
    public void removeInternalTransfer(InternalTransfer internalTransfer) {
        internalTransfers.remove(internalTransfer);
        internalTransfer.setTransfer(null);
    }

    public void addExternalTransfer(ExternalTransfer externalTransfer) {
        externalTransfers.add(externalTransfer);
        externalTransfer.setTransfer(this);
    }
    public void removeExternalTransfer(ExternalTransfer externalTransfer) {
        externalTransfers.remove(externalTransfer);
        externalTransfer.setTransfer(null);
    }
}