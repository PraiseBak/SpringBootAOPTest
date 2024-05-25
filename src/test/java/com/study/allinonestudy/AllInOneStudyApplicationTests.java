package com.study.allinonestudy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("미입력 로그인")
    void 미입력_로그인() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.view().name("login")); // Assuming login.html is in the templates directory
//                .andExpect(MockMvcResultMatchers.model().attributeExists("message")); // Assuming "message" attribute is added in the controller
    }

    @Test
    @DisplayName("입력 유효하지 않은 경우")
    void 입력_유효성_에러() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .param("username","praisebak")
                        .param("password","password")
                        .param("selfIntroduce",""))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.view().name("login")); // Assuming login.html is in the templates directory
//                .andExpect(MockMvcResultMatchers.model().attributeExists("message")); // Assuming "message" attribute is added in the controller
    }

    @Test
    @DisplayName("로그인 통과")
    void 로그인_통과() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .param("username","praisebak")
                        .param("password","asdfasdf")
                        .param("selfIntroduce","asdfasdf"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("login")); // Assuming login.html is in the templates directory
//                .andExpect(MockMvcResultMatchers.model().attributeExists("message")); // Assuming "message" attribute is added in the controller
    }


}