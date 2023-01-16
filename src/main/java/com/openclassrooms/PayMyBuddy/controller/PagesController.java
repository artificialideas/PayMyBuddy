package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PagesController {
    private UserService userService;

    @Autowired
    public void UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    String index(Model model) {
        return "login";
    }

    @GetMapping("/transfer")
    String transfer(@RequestParam(value = "id", required = true) long id, Model model) {
        model.addAttribute("contact", userService.findAllContactsByUserId(id));
        System.out.println(userService.findAllContactsByUserId(id));
        return "transfer";
    }
}
