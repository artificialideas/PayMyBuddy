package com.openclassrooms.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Contacts")
public class Contact {
    @Id
    @OneToOne
    @JoinColumn(name="id_user", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name="id_friend", nullable = false)
    private User friend;
}
