package com.study.allinonestudy.service;

import com.study.allinonestudy.dto.UserRequestDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(UserRequestDto user);
}
