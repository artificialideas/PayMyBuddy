package com.openclassrooms.PayMyBuddy.dao;

import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    ArrayList<User> findAllUsers();
}
