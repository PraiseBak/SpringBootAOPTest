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
@RequestMapping("")
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;

    @GetMapping("/")
    public String main() {
        return "main"; // This corresponds to login.html in the templates directory
    }
}
