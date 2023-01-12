package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/")
    public Iterable<User> getAllUsers() {
        return userServiceImpl.findAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(
            @PathVariable long id) {
        return userServiceImpl.findUserByID(id);
    }

//    @PostMapping("/")
//    public void add() {
//        userServiceImpl.addUser();
//    }
//
//    @DeleteMapping("/delete")
//    public void delete() {
//        userServiceImpl.deleteAllData();
//    }
}
