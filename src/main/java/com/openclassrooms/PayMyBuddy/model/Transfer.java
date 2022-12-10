package com.openclassrooms.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @OneToOne(
            fetch = FetchType.LAZY,
            mappedBy = "transfers")
    private User emitter;
}