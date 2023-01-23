package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.service.ExternalTransferService;
import com.openclassrooms.PayMyBuddy.service.InternalTransferService;
import com.openclassrooms.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/")
    String index(Model model) {
        return "login";
    }

    @GetMapping("/internalTransfer")
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

    @GetMapping("/externalTransfer")
    String externalTransfer(
            @RequestParam(value = "email", required = true) String email, Model model) {
        model.addAttribute("bank", userService.findUserByEmail(email).getBankAccounts());
        model.addAttribute("externalTransfer", externalTransferService.findExternalTransactionByUserEmail(email));
        return "externalTransfer";
    }
}