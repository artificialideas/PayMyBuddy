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
import javax.persistence.Table;

@Data
@Entity
@Table(name = "InternalTransfers")
public class InternalTransfer extends Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_internal")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "id_receiver_user",
            referencedColumnName="id_user",
            unique = true,
            nullable = false)
    private User receiver;

    /**
     *  Helper methods
     **/
    public User getUser() {
        return receiver;
    }
    public void setUser(User receiver) {
        this.receiver = receiver;
    }
}