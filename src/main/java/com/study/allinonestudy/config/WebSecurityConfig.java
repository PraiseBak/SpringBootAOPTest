package com.study.allinonestudy.config;

import com.praiseutil.timelog.utility.LogTrace;
import com.study.allinonestudy.aop.PrevLoginFilter;
import com.study.allinonestudy.helper.SessionManager;
import com.study.allinonestudy.service.UserService;
import com.study.allinonestudy.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionEventListenerManager;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig{

    private final UserService userService;
    private final SessionManager sessionManager;
    private final PrevLoginFilter prevLoginFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String errorMsg = "로그인에 실패하였습니다";
        String encodedErrorMsg = URLEncoder.encode(errorMsg, StandardCharsets.UTF_8.toString());

        http
                .csrf(httpSecurityCsrfConfigurer ->
                        httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/board").hasRole("USER")
                        .anyRequest().permitAll()
//                        .requestMatchers("/blog/**").permitAll()
//                        .anyRequest().authenticated()
                )

                .formLogin(formLogin -> {
                            formLogin
                                    .loginPage("/user/login")
                                    .defaultSuccessUrl("/")
                                    .permitAll()
                                    .successHandler(((request, response, authentication) -> {
                                        HttpSession session = request.getSession();
                                        String sessionId = session.getId();
                                        System.out.println("session id and username  " + sessionId + " ," + authentication.getName() );

                                        sessionManager.addUserSession(sessionId,authentication.getName());
                                        SecurityContextHolder.getContext().setAuthentication(authentication);
                                        response.sendRedirect("/");
                                    }))
                                    .failureHandler((request, response, exception) ->
                                            {
                                                exception.printStackTrace();
                                                response.sendRedirect("/user/login?error=" + encodedErrorMsg);
//                                                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, encodedErrorMsg);
                                            });
                        }
                )

                .userDetailsService(userService)
                //세션체크 로그인 필터 추가
                .addFilterBefore(prevLoginFilter, UsernamePasswordAuthenticationFilter.class);
//                .rememberMe(Customizer.withDefaults());

        return http.build();
    }
}
