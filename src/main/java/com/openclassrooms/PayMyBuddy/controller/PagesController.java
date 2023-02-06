package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.dto.LoginDTO;
import com.openclassrooms.PayMyBuddy.service.ExternalTransferService;
import com.openclassrooms.PayMyBuddy.service.InternalTransferService;
import com.openclassrooms.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
    //Check for Credentials
    @PostMapping("/login")
    public String login(
            @ModelAttribute(name="loginForm") LoginDTO login,
            Model model) {
        String email = login.getEmail();
        String pass = login.getPassword();
        if(email.equals("user@mail.com") && pass.equals("pass")) {
            model.addAttribute("email", email);
            model.addAttribute("password", pass);
            return "user/homepage";
        }
        model.addAttribute("error", "Incorrect Username & Password");
        return "login";

    }

    /*@RequestMapping("/homepage")
    public String index(Model model) {
        getEmail().ifPresent(d -> {
            model.addAttribute("email", userService.findUserByEmail(getEmail().toString()));
        });
        return "redirect:/user/homepage";
    }

    @RequestMapping(value = "email", method = RequestMethod.GET)
    public String userIndex(Model model) {
        getEmail().ifPresent(d -> {
            model.addAttribute("email", userService.findUserByEmail(getEmail().toString()));
        });
        return "user/homepage";
    }*/

    @GetMapping("/user/internalTransfer")
    String internalTransfer(
            @RequestParam(value = "email", required = true) String email, Model model) {
        /*List<ContactDTO> friends = userService.findAllContactsByUserEmail(email);
        List<InternalTransactionDTO> receivers = internalTransferService.findInternalTransactionByUserEmail(email)
                .stream()
                .filter(receiver -> receiver.getReceiver())
                .collect(Collectors.toList());*/

        model.addAttribute("friend", userService.findAllContactsByUserEmail(email));
        model.addAttribute("internalTransfer", internalTransferService.findInternalTransactionByUserEmail(email));
        return "internalTransfer";
    }

    @GetMapping("/user/externalTransfer")
    String externalTransfer(
            @RequestParam(value = "email", required = true) String email, Model model) {
        model.addAttribute("bank", userService.findUserByEmail(email).getBankAccounts());
        model.addAttribute("externalTransfer", externalTransferService.findExternalTransactionByUserEmail(email));
        return "externalTransfer";
    }
}