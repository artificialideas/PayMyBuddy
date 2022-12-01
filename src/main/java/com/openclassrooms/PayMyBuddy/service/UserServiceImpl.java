package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    com.openclassrooms.PayMyBuddy.dao.UserRepository UserRepository;

    @Override
    public ArrayList<User> findAllUsers() {
        return (ArrayList<User>) UserRepository.findAll();
    }

    @Override
    public User findUserByID(long id) {
        Optional<User> opt = UserRepository.findById(id);
        if (opt.isPresent())
            return opt.get();
        else
            return null;
    }

    @Override
    public void addUser() {
//        ArrayList<User> newUser = new ArrayList<User>();
//        newUser.add(new User("Lucknow", "Shubham"));
//        UserRepository.saveAll(newUser);
    }

    @Override
    public void deleteAllData() {
        UserRepository.deleteAll();
    }
}
