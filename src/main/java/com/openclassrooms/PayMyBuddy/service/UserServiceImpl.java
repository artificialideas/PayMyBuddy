package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dao.UserRepository;
import com.openclassrooms.PayMyBuddy.dto.ContactDTO;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private final ContactDTO contactDTO = new ContactDTO();

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

    @Override
    public List<ContactDTO> findAllContactsByUserId(long id) {
        List<ContactDTO> contacts = new ArrayList<>();

        List<User> contactsCollection = userRepository
                .findAll()
                .iterator()
                .next()
                .getContacts();
        for (User contactResource : contactsCollection) {
            if (contactResource.getId() != id) {
                ContactDTO contactDTO = new ContactDTO();
                contactDTO.setId(contactResource.getId());
                contactDTO.setFirstName(contactResource.getFirstName());
                contactDTO.setLastName(contactDTO.getLastName());
                contacts.add(contactDTO);
            }
        }

        return contacts;
    }
}
