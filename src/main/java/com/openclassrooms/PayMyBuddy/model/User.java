package com.openclassrooms.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private float savings;

    private List<User> friends;

    private List<InnerTransfer> innerTransfers;

    private List<ExternalTransfer> externalTransfers;
}
