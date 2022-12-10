package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.model.User;

import java.util.Optional;

public interface UserService {
    Iterable<User> findAllUsers();

    Optional<User> findUserByID(long id);

    void addUser();

    void deleteAllData();
}
