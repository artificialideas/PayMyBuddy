package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dao.UserRepository;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByID(long id) {
        Optional<User> optional = userRepository.findById(id);
        User user = null;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new RuntimeException("User not found for id : " + id);
        }
        return user;
    }
}
