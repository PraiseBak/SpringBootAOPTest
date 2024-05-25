package com.study.allinonestudy.controller;

import com.study.allinonestudy.dto.UserRequestDto;
import com.study.allinonestudy.entity.User;
import com.study.allinonestudy.helper.UserRequestValidationGroup;
import com.study.allinonestudy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("message", "Welcome to Thymeleaf!");
        return "login"; // This corresponds to login.html in the templates directory
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("message", "Signup");
        return "signup"; // This corresponds to login.html in the templates directory
    }

    @PostMapping("/signup")
    public String signup(Model model, @Validated(UserRequestValidationGroup.class) User user) {
        //userRequestDto로 입력받으면안되는 데이터 걸러냄
        UserRequestDto userRequestDto = new UserRequestDto(user);
        userService.save(userRequestDto);
        return "main"; // This corresponds to login.html in the templates directory
    }
}
