package com.study.allinonestudy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.study.allinonestudy.config.ErrorAdviceController;
import com.study.allinonestudy.controller.BoardController;
import com.study.allinonestudy.controller.UserController;
import com.study.allinonestudy.entity.Board;
import com.study.allinonestudy.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTests {

    @Autowired
    private ObjectMapper objectMapper; // 객체를 JSON으로 변환하기 위한 ObjectMapper

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private BoardController boardController;

    @Autowired ErrorAdviceController errorAdviceController;

    @Autowired
    WebApplicationContext context;

    BoardControllerTests() throws JsonProcessingException {
    }

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    }

    @Test
    @DisplayName("게시물 조회 페이징 테스트")
    @WithUserDetails(value = "praisebak", userDetailsServiceBeanName = "userServiceImpl")
    void 게시물_조회_페이징_테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/board/?page=1"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    @DisplayName("게시물 작성")
    @WithUserDetails(value = "praisebak", userDetailsServiceBeanName = "userServiceImpl")
    void 게시물_작성() throws Exception {
        Board board = Board.builder()
                .title("작성 성공 테스트")
                .content("작성 성공")
                .build();

        String json = objectMapper.writeValueAsString(board);

        MockMultipartFile file = new MockMultipartFile(
                "file",                      // 파라미터 이름
                "test.txt",                  // 원래 파일 이름
                MediaType.TEXT_PLAIN_VALUE,  // 파일 유형
                "This is the file content.".getBytes()  // 파일 내용
        );
        mockMvc.perform(MockMvcRequestBuilders.multipart("/board/")
                        .file(file)
                        .file(new MockMultipartFile("board", "", "application/json", json.getBytes()))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        board = Board.builder()
                .title("")
                .content("")
                .build();

        json = objectMapper.writeValueAsString(board);
        mockMvc.perform(MockMvcRequestBuilders.post("/board/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        board = Board.builder()
                .title("작")
                .content("성")
                .build();

        json = objectMapper.writeValueAsString(board);
        mockMvc.perform(MockMvcRequestBuilders.post("/board/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}