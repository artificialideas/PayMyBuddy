package com.openclassrooms.PayMyBuddy.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name="InnerTransfer")
public class InnerTransfer extends Transfer {
    private User receiver;
}
