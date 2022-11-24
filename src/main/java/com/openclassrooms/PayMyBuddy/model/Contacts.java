package com.openclassrooms.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "Contacts")
public class Contacts {
    @Column(name="id_user")
    private User id_user;

    @Column(name="id_friend")
    private List<User> friends;
}
