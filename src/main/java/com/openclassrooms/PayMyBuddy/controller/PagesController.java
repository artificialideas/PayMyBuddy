package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.service.ExternalTransferService;
import com.openclassrooms.PayMyBuddy.service.InternalTransferService;
import com.openclassrooms.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

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
        return "redirect:/homepage";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    private Optional<String> getEmail() {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        String email = null;
        if (auth != null && !auth.getClass().equals(AnonymousAuthenticationToken.class)) {
            User user = (User) auth.getPrincipal();
            email = user.getEmail();
        }
        return Optional.ofNullable(email);
    }

    @RequestMapping("/homepage")
    public String index(Model model) {
        getEmail().ifPresent(d -> {
            model.addAttribute("email", d);
        });
        return "index";
    }

    @RequestMapping("/user/homepage")
    public String userIndex(Model model) {
        getEmail().ifPresent(d -> {
            model.addAttribute("email", d);
        });
        return "user/homepage";
    }

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