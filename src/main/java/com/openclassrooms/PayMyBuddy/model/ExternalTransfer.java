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
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ExternalTransfers")
public class ExternalTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_externaltrans")
    private long id;

    @Column(name = "emission_date", nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "text_description")
    private String description;

    @ManyToOne
    @JoinColumn(
            name = "id_emitter_user",
            unique = true,
            nullable = false)
    private User emitter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "id_receiver_bank",
            unique = true,
            nullable = false)
    private BankAccount bankAccount;

    /**
     *  Helper methods
     **/
    /* @ManyToOne -> emitter */
    public User getEmitter() {
        return emitter;
    }
    public void setEmitter(User emitter) {
        this.emitter = emitter;
    }

    /* @OneToOne -> bankAccount */
    public BankAccount getBankAccount() {
        return bankAccount;
    }
    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
