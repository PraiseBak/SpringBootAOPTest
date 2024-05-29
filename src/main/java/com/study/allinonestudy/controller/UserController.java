package com.study.allinonestudy.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.study.allinonestudy.dto.UserRequestDto;
import com.study.allinonestudy.entity.User;
import com.study.allinonestudy.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("message", "Welcome to Thymeleaf!");
        return "login"; // This corresponds to login.html in the templates directory
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("message", "Signup");
        return "signup"; // This corresponds to login.html in the templates directory
    }

    @PostMapping("/signup")
    public String signupUser(User user) {
        //userRequestDto로 입력받으면안되는 데이터 걸러냄
        UserRequestDto userRequestDto = new UserRequestDto(user);
        userService.save(userRequestDto);
        return "login"; // This corresponds to login.html in the templates directory
    }


    //유저 로그아웃
    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        if(sessionId != null){
            userService.logout(sessionId);
        }
        return "redirect:/user/login";
    }
}

