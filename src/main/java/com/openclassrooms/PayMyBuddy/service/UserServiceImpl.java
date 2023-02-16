package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dao.UserRepository;
import com.openclassrooms.PayMyBuddy.dto.ContactDTO;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmailAndPassword(String email) {
        User user = new User();
        Iterable<User> users = findAllUsers();
        for (User u : users) {
            if (Objects.equals(u.getEmail(), email)) {
                user.setEmail(u.getEmail());
                user.setPassword(u.getPassword());
                break;
            }
        }

        if (user.getEmail() == null) {
            throw new RuntimeException("User not found with email " + email);
        }

        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = new User();
        Iterable<User> users = findAllUsers();
        for (User u : users) {
            if (Objects.equals(u.getEmail(), email)) {
                user.setEmail(u.getEmail());
                user.setFirstName(u.getFirstName());
                user.setLastName(u.getLastName());
                break;
            }
        }

        if (user.getEmail() == null) {
            throw new RuntimeException("User not found with email " + email);
        }

        return user;
    }

    @Override
    public List<ContactDTO> findAllContactsByUserEmail(String email) {
        List<ContactDTO> contacts = new ArrayList<>();

        List<User> contactsCollection = userRepository
                .findAll()
                .iterator()
                .next()
                .getContacts();
        for (User contactResource : contactsCollection) {
            if (!Objects.equals(contactResource.getEmail(), email)) {
                ContactDTO contactDTO = new ContactDTO();
                contactDTO.setFirstName(contactResource.getFirstName());
                contactDTO.setLastName(contactResource.getLastName());
                contactDTO.setEmail(contactResource.getEmail());
                contacts.add(contactDTO);
            }
        }

        return contacts;
    }
}
