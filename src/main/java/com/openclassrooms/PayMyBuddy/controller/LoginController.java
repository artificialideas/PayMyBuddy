package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.dto.UserDetailsDTO;
import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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

    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @PostMapping("/register/create")
    public String addUser(
            @Valid User newUser,
            BindingResult result,
            Model model) {
        if (newUser != null && !(result.hasErrors())) {
            userService.create(newUser);
            return "redirect:/login?newUser";
        } else
        return "redirect:/register?error";
    }

    /**
     * HOMEPAGE
     */
    @RequestMapping("/user/index")
    public String userIndex(Authentication authentication, Model model) {
        String email = authentication.getName();
        UserDetailsDTO user = userService.findUserByEmail(email);

        model.addAttribute("user", user);
        return SECURED_URL + "/index";
    }
}