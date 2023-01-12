package com.openclassrooms.PayMyBuddy.dao;

import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    //List<User> findAllUsers();
}
