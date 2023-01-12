package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.model.User;

public interface UserService {
    Iterable<User> findAllUsers();

    User findUserByID(long id);
}
