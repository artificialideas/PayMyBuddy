package com.openclassrooms.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
abstract class Transfer {
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

    /**
     *  Helper methods
     **/
    /* @ManyToOne -> emitter */
    public User getUser() {
        return emitter;
    }
    public void setUser(User emitter) {
        this.emitter = emitter;
    }
}