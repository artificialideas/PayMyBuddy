package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dto.ContactDTO;
import com.openclassrooms.PayMyBuddy.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Iterable<User> findAllUsers();

    Optional<User> findUserById(Long id);

    User findUserByEmailAndPassword(String email);

    User findUserByEmail(String email);

    List<ContactDTO> findAllContactsByUserEmail(String email);
}
