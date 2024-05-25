package com.study.allinonestudy.service;

import com.study.allinonestudy.dto.UserRequestDto;
import com.study.allinonestudy.entity.User;
import com.study.allinonestudy.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("username = {} 유저를 찾을 수 없습니다",username)
        ));
        System.out.println(String.format("username = {} 유저를 찾았습니다.",username));
        return user;
    }

    @Override
    public void save(UserRequestDto userRequestDto){
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        User user = new com.study.allinonestudy.entity.User(userRequestDto);
        userRepository.save(user);
    }
}
