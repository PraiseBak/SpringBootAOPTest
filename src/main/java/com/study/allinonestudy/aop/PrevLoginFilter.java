package com.study.allinonestudy.aop;


import com.study.allinonestudy.entity.User;
import com.study.allinonestudy.helper.Role;
import com.study.allinonestudy.helper.SessionManager;
import com.study.allinonestudy.service.UserService;
import com.study.allinonestudy.service.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Filter;
import org.modelmapper.internal.bytebuddy.build.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PrevLoginFilter extends OncePerRequestFilter {

    private final SessionManager sessionManager;

    private final UserService userService;
    
    private static final int inactiveTime = 60 * 30;

    //알아서 session의 남은 시간은 갱신됨
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        String username = sessionManager.getUsernameBySessionId(sessionId);

        //username있으면 로그인된것이라 가정
        if(username != null) {
            User user = userService.findByUsername(username);
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}