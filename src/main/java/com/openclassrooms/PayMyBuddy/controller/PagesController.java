package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.dto.UserDetailsDTO;
import com.openclassrooms.PayMyBuddy.model.BankAccount;
import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.service.BankAccountService;
import com.openclassrooms.PayMyBuddy.service.ExternalTransferService;
import com.openclassrooms.PayMyBuddy.service.InternalTransferService;
import com.openclassrooms.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user/")
public class PagesController {
    private final String SECURED_URL = "user";

    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private InternalTransferService internalTransferService;
    @Autowired
    private ExternalTransferService externalTransferService;

    /**
     * TRANSFERS
     */
    /* Internal transfers */
    @GetMapping("/internalTransfer")
    String internalTransfer(Authentication authentication, Model model) {
        String email = authentication.getName();
        Long id = userService.findUserByEmail(email).getId();

        model.addAttribute("friend", userService.findContactsByUserEmail(email));
        model.addAttribute("internalTransfer", internalTransferService.findInternalTransferByUserId(id));
        return  SECURED_URL + "/internalTransfer";
    }

    /* External transfers */
    @GetMapping("/externalTransfer")
    String externalTransfer(Authentication authentication, Model model) {
        String email = authentication.getName();
        Long id = userService.findUserByEmail(email).getId();

        model.addAttribute("bank", userService.findBankAccountsByUserId(id));
        model.addAttribute("externalTransfer", externalTransferService.findExternalTransactionByUserId(id));
        return  SECURED_URL + "/externalTransfer";
    }


    /**
     * PROFILE
     */
    @GetMapping("/profile")
    String profile(Authentication authentication, Model model) {
        String email = authentication.getName();
        UserDetailsDTO user = userService.findUserByEmail(email);

        model.addAttribute("user", user);
        model.addAttribute("bank", userService.findBankAccountsByUserId(user.getId()));
        model.addAttribute("friend", userService.findContactsByUserEmail(email));
        return SECURED_URL + "/profile";
    }
    /* Edit info */
    @PostMapping("/profile/update")
    public String updateUser(
            @Valid User userToUpdate,
            BindingResult result,
            Authentication authentication,
            Model model) {
        UserDetailsDTO savedUser = userService.findUserByEmail(authentication.getName());

        if (userToUpdate != null && savedUser != null) {
            userService.update(userToUpdate, savedUser.getId());
        }
        return "redirect:/user/profile";
    }

    /* Bank accounts */
    @PostMapping("/profile/addBankAccount")
    public String addBankAccount(
            @Valid BankAccount newBankAccount,
            BindingResult result,
            Authentication authentication,
            Model model) {
        if (result.hasErrors()) {
            return "addBankAccount";
        }
        // We'll create User owner through authenticated user
        String email = authentication.getName();
        Long userId = userService.findUserByEmail(email).getId();

        bankAccountService.add(newBankAccount, userId);
        return "redirect:/user/profile";
    }
    @GetMapping("/profile/deleteBankAccount/{id}")
    public String deleteBankAccount(
            @PathVariable("id") long id,
            Model model) {
        BankAccount bankAccount = bankAccountService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bank account Id: " + id));

        bankAccountService.delete(bankAccount);
        return "redirect:/user/profile";
    }

    /* Connections */
    @PostMapping("/profile/addFriend")
    public String addFriend(
            @Valid String friendEmail,
            BindingResult result,
            Authentication authentication,
            Model model) {
        if (result.hasErrors()) {
            return "addFriend";
        }
        // We'll create User owner through authenticated user
        String email = authentication.getName();
        Long userId = userService.findUserByEmail(email).getId();
        Long contactId = userService.findUserByEmail(friendEmail).getId();

        userService.addContact(userId, contactId);
        return "redirect:/user/profile";
    }
    @GetMapping("/profile/deleteFriend/{email}")
    public String deleteFriend(
            @PathVariable("email") String contactEmail,
            Authentication authentication,
            Model model) {
        String userEmail = authentication.getName();
        Long userId = userService.findUserByEmail(userEmail).getId();
        Long contactId = userService.findUserByEmail(contactEmail).getId();

        userService.deleteContact(userId, contactId);
        return "redirect:/user/profile";
    }
}