package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dao.UserRepository;
import com.openclassrooms.PayMyBuddy.dto.BankAccountDTO;
import com.openclassrooms.PayMyBuddy.dto.ContactDTO;
import com.openclassrooms.PayMyBuddy.dto.UserDetailsDTO;
import com.openclassrooms.PayMyBuddy.model.BankAccount;
import com.openclassrooms.PayMyBuddy.model.ExternalTransfer;
import com.openclassrooms.PayMyBuddy.model.InternalTransfer;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findUserByEmailAndPassword(String email) {
        User user = new User();
        Iterable<User> users = findAll();
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
    public UserDetailsDTO findUserByEmail(String email) {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        List<ContactDTO> contacts = new ArrayList<>();

        Iterable<User> users = findAll();
        for (User u : users) {
            if (Objects.equals(u.getEmail(), email)) {
                for (User contact : u.getContacts()) {
                    ContactDTO contactDTO = new ContactDTO();
                        contactDTO.setEmail(contact.getEmail());
                        contactDTO.setFirstName(contact.getFirstName());
                        contactDTO.setLastName(contact.getLastName());
                    contacts.add(contactDTO);
                }

                userDetailsDTO.setId(u.getId());
                userDetailsDTO.setFirstName(u.getFirstName());
                userDetailsDTO.setLastName(u.getLastName());
                userDetailsDTO.setEmail(u.getEmail());
                userDetailsDTO.setContacts(contacts);
                break;
            }
        }

        if (userDetailsDTO.getEmail() == null) {
            throw new RuntimeException("User not found with email " + email);
        }

        return userDetailsDTO;
    }

    @Override
    public void update(User userToUpdate, Long id) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        userToUpdate.setId(savedUser.getId());
        if (user.getEmail() != null && !(user.getEmail().equals(savedUser.getEmail())))
            savedUser.setEmail(userToUpdate.getEmail());
        if (user.getFirstName() != null && !(user.getFirstName().equals(savedUser.getFirstName())))
            savedUser.setFirstName(userToUpdate.getFirstName());
        if (user.getLastName() != null && !(user.getLastName().equals(savedUser.getLastName())))
            savedUser.setLastName(userToUpdate.getLastName());
        if (user.getPassword() != null)
            savedUser.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));

        userRepository.save(user);
    }

    @Override
    public List<ContactDTO> findContactsByUserEmail(String email) {
        UserDetailsDTO user = findUserByEmail(email);

        return new ArrayList<>(user.getContacts());
    }

    @Override
    public List<BankAccountDTO> findBankAccountsByUserId(Long id) {
        List<BankAccountDTO> bankAccountDTOs = new ArrayList<>();
        Optional<User> owner = findById(id);
        List<BankAccount> bankAccounts = owner.map(User::getBankAccounts).orElse(null);

        assert bankAccounts != null;
        for (BankAccount account : bankAccounts) {
            BankAccountDTO bankAccountDTO = new BankAccountDTO();
                bankAccountDTO.setId(account.getId());
                bankAccountDTO.setEmail(owner.get().getEmail());
                bankAccountDTO.setCredentials(account.getCredentials());
                bankAccountDTO.setIban(account.getIban());
                bankAccountDTO.setSwift(account.getSwift());
            bankAccountDTOs.add(bankAccountDTO);
        }

        return bankAccountDTOs;
    }

    @Override
    public List<InternalTransfer> findInternalTransferByUserId(Long id) {
        Optional<User> emitter = findById(id);

        return emitter.map(User::getInternalTransfers).orElse(null);
    }

    @Override
    public List<ExternalTransfer> findExternalTransferByUserId(Long id) {
        Optional<User> emitter = findById(id);

        return emitter.map(User::getExternalTransfers).orElse(null);
    }

    @Override
    public User addContact(Long userId, Long contactId) {
        Optional<User> user = findById(userId);
        Optional<User> contact = findById(contactId);
        //user.addContact(contact);

        return null;//userRepository.save(user);
    }

    @Override
    public void deleteContact(Long userId, Long contactId) {
        List<User> contactsToSave = new ArrayList<>();
        Optional<User> user = findById(userId);

        // ToDo: set new contacts to user and update -> userRepository.save(user)
        if (user.isPresent()) {
            contactsToSave = user.get()
                    .getContacts()
                    .stream()
                    .filter(co -> co.getId() != contactId)
                    .collect(Collectors.toList());
        }
        // Maybe a removeContact()?
        /*Optional<User> contact = findById(contactId);
        user.removeContact(contact);*/

        /*user.ifPresent(u -> u
                .getContacts()
                .stream()
                .filter(co -> co.getId() != contactId)
                .collect(Collectors.toList()));*/
    }
}
