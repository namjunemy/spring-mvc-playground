package io.namjune.springmvcplayground.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class DemoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void helloUriTest() throws Exception {
        this.mockMvc.perform(get("/hello/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("hello uri"))
            .andExpect(handler().handlerType(DemoController.class))
            .andExpect(handler().methodName("helloUri"));

        this.mockMvc.perform(get("/hello/nj"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("hello uri"))
            .andExpect(handler().handlerType(DemoController.class))
            .andExpect(handler().methodName("helloUri"));

        this.mockMvc.perform(get("/hello/user/nj"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("hello uri"))
            .andExpect(handler().handlerType(DemoController.class))
            .andExpect(handler().methodName("helloUri"));

        // URI 확장자 맵핑 지원은 스프링 부트에서는 기본적으로 비활성화
        // 최근 추세는 헤더 정보를 보고 판단
        this.mockMvc.perform(get("/hello.zip"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    void helloTest() throws Exception {
        this.mockMvc.perform(get("/hello"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("hello"))
            .andExpect(handler().handlerType(DemoController.class))
            .andExpect(handler().methodName("hello"));
    }
}