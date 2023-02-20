package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.service.ExternalTransferService;
import com.openclassrooms.PayMyBuddy.service.InternalTransferService;
import com.openclassrooms.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class PagesController {
    private UserService userService;
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

        model.addAttribute("friend", userService.findAllContactsByUserEmail(email));
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
        User user = userService.findUserByEmail(email);

        model.addAttribute("user", user);
        return SECURED_URL + "/profile";
    }
}