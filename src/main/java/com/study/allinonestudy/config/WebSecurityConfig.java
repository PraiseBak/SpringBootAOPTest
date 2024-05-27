package com.study.allinonestudy.config;

import com.praiseutil.timelog.utility.LogTrace;
import com.study.allinonestudy.service.UserService;
import com.study.allinonestudy.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String errorMsg = "로그인에 실패하였습니다.";
        String encodedErrorMsg = URLEncoder.encode(errorMsg, StandardCharsets.UTF_8.toString());

        http
                .csrf(httpSecurityCsrfConfigurer ->
                        httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
//                        .requestMatchers("/blog/**").permitAll()
//                        .anyRequest().authenticated()
                )

                .formLogin(formLogin -> {
                            formLogin
                                    .loginPage("/user/login")
                                    .defaultSuccessUrl("/")
                                    .permitAll()
                                    .failureHandler((request, response, exception) ->
                                            {
                                                exception.printStackTrace();
                                                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, encodedErrorMsg);
                                            });
//                                            response.sendRedirect("/user/login?error=" + encodedErrorMsg));
                        }
                )

                .userDetailsService(userService)
                .rememberMe(Customizer.withDefaults());

        return http.build();
    }
}
