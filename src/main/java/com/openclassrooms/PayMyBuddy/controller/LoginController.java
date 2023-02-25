package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.dto.UserDetailsDTO;
import com.openclassrooms.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    private final String SECURED_URL = "user";

    @Autowired
    private UserService userService;

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
        UserDetailsDTO user = userService.findUserByEmail(email);

        model.addAttribute("user", user);
        return SECURED_URL + "/index";
    }
}