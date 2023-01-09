package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private com.openclassrooms.PayMyBuddy.dao.UserRepository UserRepository;

    @Override
    public Iterable<User> findAllUsers() {
        return UserRepository.findAll();
    }

    @Override
    public Optional<User> findUserByID(long id) {
        return UserRepository.findById(id);
    }

    /*@Override
    public void addUser() {
//        ArrayList<User> newUser = new ArrayList<User>();
//        newUser.add(new User("Lucknow", "Shubham"));
//        UserRepository.saveAll(newUser);
    }

    @Override
    public void deleteAllData() {
        UserRepository.deleteAll();
    }*/
}
