package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.dto.UserDetailsDTO;
import com.openclassrooms.PayMyBuddy.model.BankAccount;
import com.openclassrooms.PayMyBuddy.service.BankAccountService;
import com.openclassrooms.PayMyBuddy.service.ExternalTransferService;
import com.openclassrooms.PayMyBuddy.service.InternalTransferService;
import com.openclassrooms.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/user/")
public class PagesController {
    private UserService userService;
    private BankAccountService bankAccountService;
    private InternalTransferService internalTransferService;
    private ExternalTransferService externalTransferService;

    private final String SECURED_URL = "user";

    @Autowired
    public void UserController(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void InternalTransferController(InternalTransferService internalTransferService) {
        this.internalTransferService = internalTransferService;
    }
    @Autowired
    public void ExternalTransferController(ExternalTransferService externalTransferService) {
        this.externalTransferService = externalTransferService;
    }

    @GetMapping("/internalTransfer")
    String internalTransfer(Authentication authentication, Model model) {
        String email = authentication.getName();
        Long id = userService.findUserByEmail(email).getId();

        model.addAttribute("friend", userService.findContactsByUserEmail(email));
        model.addAttribute("internalTransfer", internalTransferService.findInternalTransferByUserId(id));
        return  SECURED_URL + "/internalTransfer";
    }

    @GetMapping("/externalTransfer")
    String externalTransfer(Authentication authentication, Model model) {
        String email = authentication.getName();
        Long id = userService.findUserByEmail(email).getId();

        model.addAttribute("bank", userService.findBankAccountsByUserId(id));
        model.addAttribute("externalTransfer", externalTransferService.findExternalTransactionByUserId(id));
        return  SECURED_URL + "/externalTransfer";
    }

    @GetMapping("/profile")
    String profile(Authentication authentication, Model model) {
        String email = authentication.getName();
        UserDetailsDTO user = userService.findUserByEmail(email);

        model.addAttribute("user", user);
        model.addAttribute("bank", userService.findBankAccountsByUserId(user.getId()));
        return SECURED_URL + "/profile";
    }
    @PostMapping("/profile/addBankAccount")
    public ResponseEntity<BankAccount> addBankAccount(
            @RequestBody BankAccount newBankAccount) {

        BankAccount bankAccount = bankAccountService.add(newBankAccount);
        if (Objects.isNull(bankAccount)) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(bankAccount, HttpStatus.CREATED);
        }
    }
    @DeleteMapping("/profile/deleteBankAccount")
    public String deleteBankAccount(
            @PathVariable String id) {
System.out.println(id);
        //bankAccountService.deleteById(id);
        return "redirect:/profile";
    }
}