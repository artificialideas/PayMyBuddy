package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.model.User;

import java.util.ArrayList;

public interface UserService {
    ArrayList<User> findAllUsers();

    User findUserByID(long id);

    void addUser();

    void deleteAllData();
}
