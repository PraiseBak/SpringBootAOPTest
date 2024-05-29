package com.study.allinonestudy.service;

import com.study.allinonestudy.dto.UserRequestDto;
import com.study.allinonestudy.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(UserRequestDto user);

    void logout(String sessionId);

    User findByUsername(String username);


}
