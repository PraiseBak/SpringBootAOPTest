package com.study.allinonestudy.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SessionManager {
    private Map<String,String> sessionMap = new HashMap<>();


    public void addUserSession(String id,String username){
        sessionMap.put(id,username);
    }

    public void removeUserSession(String id){
        sessionMap.remove(id);
    }

    public String getUsernameBySessionId(String sessionId) {
        return sessionMap.get(sessionId);

    }
}
