package com.openclassrooms.PayMyBuddy.model;

import lombok.Data;

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

@Data
@Entity
@Table(name = "InternalTransfers")
public class InternalTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_internaltrans")
    private long id;

    @Column(name = "emission_date", nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(
            name = "id_emitter_user",
            unique = true,
            nullable = false)
    private User emitter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "id_receiver_user",
            unique = true,
            nullable = false)
    private User receiver;

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

    /* @OneToOne -> receiver */
    public User getReceiver() {
        return receiver;
    }
    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}