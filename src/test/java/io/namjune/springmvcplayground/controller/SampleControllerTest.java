package io.namjune.springmvcplayground.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    @Test
    @DisplayName("hello")
    void hello() throws Exception {
        this.mockMvc.perform(get("/hello"))
            .andDo(print())
            .andExpect(content().string("hello "));
    }

    @Test
    @DisplayName("hello name")
    void helloNamePath() throws Exception {
        this.mockMvc.perform(get("/hello/nj"))
            .andDo(print())
            .andExpect(content().string("hello nj"));
    }

    @Test
    @DisplayName("hello name")
    void helloNameParam() throws Exception {
        this.mockMvc.perform(get("/hello")
                                 .param("name", "nj"))
            .andDo(print())
            .andExpect(content().string("hello nj"));
    }
}