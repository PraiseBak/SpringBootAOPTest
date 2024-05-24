package com.study.allinonestudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public String welcome(Model model) {
        model.addAttribute("message", "Welcome to Thymeleaf!");
        return "login"; // This corresponds to login.html in the templates directory
    }
}
