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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PagesController {
    private UserService userService;
    private InternalTransferService internalTransferService;
    private ExternalTransferService externalTransferService;

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

    @RequestMapping("/")
    public String root() {
        return "redirect:/login";
    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/user/index")
    public String userIndex(Authentication authentication, Model model) {
        String email = authentication.getName();

        User user = userService.findUserByEmail(email);
        model.addAttribute("user", user);

        return "user/index";
    }

    @GetMapping("/user/internalTransfer")
    String userInternalTransfer(Authentication authentication, Model model) {
        String email = authentication.getName();
        Long id = userService.findUserByEmail(email).getId();

        model.addAttribute("friend", userService.findAllContactsByUserEmail(email));
        model.addAttribute("internalTransfer", internalTransferService.findInternalTransferByUserId(id));
        return "/user/internalTransfer";
    }

    @GetMapping("/user/externalTransfer")
    String externalTransfer(
            @RequestParam(value = "email", required = true) String email, Model model) {
        model.addAttribute("bank", userService.findUserByEmail(email).getBankAccounts());
        model.addAttribute("externalTransfer", externalTransferService.findExternalTransactionByUserEmail(email));
        return "/user/externalTransfer";
    }
}