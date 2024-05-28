package com.study.allinonestudy.service;

import com.study.allinonestudy.dto.UserRequestDto;
import com.study.allinonestudy.entity.User;
import com.study.allinonestudy.helper.SessionManager;
import com.study.allinonestudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionManager sessionManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("username = %s 유저를 찾을 수 없습니다",username)
        ));

        return user;
//        System.out.println(String.format("username = %s 유저를 찾았습니다.",username));
//        return User.builder()
//                .username(user.getUsername())
//                .password(passwordEncoder.encode(user.getPassword()))
//                .roles(user.getRoles())
//                .build();
    }

    @Override
    public void save(UserRequestDto userRequestDto){
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        User user = new com.study.allinonestudy.entity.User(userRequestDto);
        userRepository.save(user);
    }

    @Override
    public void logout(String sessionId) {
        sessionManager.removeUserSession(sessionId);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다"));
    }
}
