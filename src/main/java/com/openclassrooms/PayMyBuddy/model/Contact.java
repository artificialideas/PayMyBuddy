package com.openclassrooms.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Contacts")
public class Contact implements Serializable {
    @Id
    @ManyToOne(targetEntity= User.class)
    @JoinColumn(name="id_user", nullable = false)
    private User user;

    @ManyToOne(targetEntity= User.class)
    @JoinColumn(name="id_friend", nullable = false)
    private User friend;
}
