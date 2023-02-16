package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dao.UserRepository;
import com.openclassrooms.PayMyBuddy.dto.ContactDTO;
import com.openclassrooms.PayMyBuddy.model.InternalTransfer;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
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
                user.setId(u.getId());
                user.setFirstName(u.getFirstName());
                user.setLastName(u.getLastName());
                user.setEmail(u.getEmail());
                user.setContacts(u.getContacts());
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
        User user = findUserByEmail(email);

        for (User contact : user.getContacts()) {
            ContactDTO contactDTO = new ContactDTO();
                contactDTO.setEmail(contact.getEmail());
                contactDTO.setFirstName(contact.getFirstName());
                contactDTO.setLastName(contact.getLastName());
            contacts.add(contactDTO);
        }

        return contacts;
    }

    @Override
    public List<InternalTransfer> findInternalTransferByUserId(Long id) {
        Optional<User> emitter = findUserById(id);

        return emitter.map(User::getInternalTransfers).orElse(null);
    }
}
