package com.openclassrooms.PayMyBuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {
    private String user = "Mimi";

    @GetMapping("/")
    String index(Model model) {
        model.addAttribute("user", user);
        return "login";
    }
}
