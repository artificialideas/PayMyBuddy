package com.openclassrooms.PayMyBuddy.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDetailsDTO {
    private String firstName;
    private String lastName;
    private String email;
    private List<ContactDTO> contacts;
}
