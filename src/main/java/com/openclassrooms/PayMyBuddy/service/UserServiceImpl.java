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
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email " + email);
        }

        return user;
    }

    @Override
    public UserDetailsDTO findUserByEmail(String email) {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        List<ContactDTO> contacts = new ArrayList<>();
        Currency currency = Currency.getInstance("EUR");

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
                userDetailsDTO.setSavings(u.getSavings().setScale(2, RoundingMode.HALF_EVEN) + currency.getSymbol());
                break;
            }
        }

        if (userDetailsDTO.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email " + email);
        }

        return userDetailsDTO;
    }

    @Override
    public void create(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @Override
    public void update(User userToUpdate, Long id) {
        User savedUser = new User();

        Optional<User> optSavedUser = findById(id);
        if (optSavedUser.isPresent()) savedUser = optSavedUser.get();

        if (userToUpdate.getEmail() != null)
            savedUser.setEmail(userToUpdate.getEmail());
        if (userToUpdate.getFirstName() != null)
            savedUser.setFirstName(userToUpdate.getFirstName());
        if (userToUpdate.getLastName() != null)
            savedUser.setLastName(userToUpdate.getLastName());

        userRepository.save(savedUser);
    }

    @Override
    public void save(User user) {
        // Check just in case
        Optional<User> userToSave = findById(user.getId());
        if (userToSave.isPresent())
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
        User updatedUser = new User();
        User newContact = new User();

        Optional<User> user = findById(userId);
        Optional<User> contact = findById(contactId);

        // Validate that both users exists & that contact is not in user's contacts list
        if (user.isPresent() && contact.isPresent()
            && !(user.get().getContacts().contains(contact.get()))) {
            updatedUser = user.get();
            newContact = contact.get();

            updatedUser.getContacts().add(newContact);
        } else if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        else if (contact.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found");
        else
            throw new ResponseStatusException(HttpStatus.IM_USED, "This contact is already in user's contacts lists");

        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteContact(Long userId, Long contactId) {
        User updatedUser = new User();
        User removeContact = new User();

        Optional<User> user = findById(userId);
        Optional<User> contact = findById(contactId);

        // Validate that both users exists & that contact is in user's contacts list
        if (user.isPresent() && contact.isPresent()
            && user.get().getContacts().contains(contact.get())) {
            updatedUser = user.get();
            removeContact = contact.get();

            updatedUser.getContacts().remove(removeContact);
        } else if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        else if (contact.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found");
        else
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This contact is not in user's contacts lists");

        userRepository.save(updatedUser);
    }
}
