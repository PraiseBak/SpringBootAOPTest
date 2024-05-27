package com.study.allinonestudy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.praiseutil.timelog.config.AppConfig;
import com.praiseutil.timelog.utility.LogTrace;
import com.study.allinonestudy.aop.TimeLogAOP;
import com.study.allinonestudy.config.ErrorAdviceController;
import com.study.allinonestudy.controller.UserController;
import com.study.allinonestudy.dto.UserRequestDto;
import com.study.allinonestudy.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcSnippetConfigurer;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.test.web.servlet.setup.MockMvcConfigurerAdapter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

    @Autowired
    private ObjectMapper objectMapper; // 객체를 JSON으로 변환하기 위한 ObjectMapper

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Autowired ErrorAdviceController errorAdviceController;

    @Autowired
    WebApplicationContext context;

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
//                .alwaysExpect(status().isOk())
                .build();
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    }

    @Test
    @DisplayName("미입력 로그인")
    void 미입력_로그인() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    @DisplayName("입력 유효하지 않은 경우")
    void 입력_유효성_에러() throws Exception {
        mockMvc.perform(formLogin("/user/login")
                        .user("username")
                        .password("praisebak")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    @DisplayName("로그인 통과")
    void 로그인_통과() throws Exception {
        User user = User.builder()
                        .selfIntroduce("asdfasdf")
                        .username("praisebak")
                        .password("asdfasdf")
                        .build();

        String json =  objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                    .andExpect(MockMvcResultMatchers.view().name("login")); // Assuming login.html is in the templates directory
                .andExpect(MockMvcResultMatchers.model().attributeExists("message")); // Assuming "message" attribute is added in the controller


        mockMvc.perform(formLogin("/user/login")
                        .user("praisebak")
                        .password("asdfasdf"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
//                .andExpect(MockMvcResultMatchers.view().name("main")); // Assuming login.html is in the templates directory
    }


}