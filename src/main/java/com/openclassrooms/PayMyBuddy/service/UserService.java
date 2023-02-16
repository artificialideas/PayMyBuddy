package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dto.ContactDTO;
import com.openclassrooms.PayMyBuddy.model.User;

import java.util.List;

public interface UserService {
    Iterable<User> findAllUsers();

    User findUserByEmailAndPassword(String email);

    User findUserByEmail(String email);

    List<ContactDTO> findAllContactsByUserEmail(String email);
}
