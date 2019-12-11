package io.namjune.springmvcplayground.controller;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class DemoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void httpOptionsMethodTest() throws Exception {
        // HTTP OPTIONS 메소드로 요청시 Headers응답에 Allow 속성으로 정의된 HTTP Method 정보를 얻을 수 있다.
        // GET, POST는 정의한 메소드. HEAD, OPTIONS는 스프링 MVC에서 제공하는 메소드
        this.mockMvc.perform(options("/hello"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().exists(HttpHeaders.ALLOW))
            .andExpect(header().stringValues(HttpHeaders.ALLOW, hasItems(containsString("GET"),
                                                                         containsString("POST"),
                                                                         containsString("HEAD"),
                                                                         containsString("OPTIONS"))));
    }

    @Test
    void httpHeadMethodTest() throws Exception {
        // HTTP HEAD 메소드로 요청시 응답 본문은 넘어오지 않는다.
        this.mockMvc.perform(head("/hello"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(Strings.EMPTY));

        this.mockMvc.perform(get("/hello"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("hello"));
    }

    @Test
    void helloParamTest() throws Exception {
        // helloParam 핸들러는 parameter name이 hacker가 아닌 경우에만 발동
        this.mockMvc.perform(get("/helloParam")
                                 .param("name", "hacker"))
            .andDo(print())
            .andExpect(status().isBadRequest());

        this.mockMvc.perform(get("/helloParam")
                                 .param("name", "namjune"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("hello namjune"));
    }

    @Test
    void helloHeaderTest() throws Exception {
        // 헤더에 FROM 정보가 있는 경우에만 핸들러 발동
        this.mockMvc.perform(get("/helloHeader")
                                 .header(HttpHeaders.AUTHORIZATION, "ADMIN"))
            .andDo(print())
            .andExpect(status().isNotFound());

        this.mockMvc.perform(get("/helloHeader")
                                 .header(HttpHeaders.FROM, "localhost"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("hello header"))
            .andExpect(handler().methodName("helloHeader"));
    }

    @Test
    void helloJsonToTextResponseTest() throws Exception {
        // 요청 - 응답으로 JSON 요청 하지만 JSON 응답을 만드는 핸들러가 존재하지 않음. 406 Not Acceptable
        this.mockMvc.perform(get("/helloJsonToTextResponse")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .accept(MediaType.APPLICATION_JSON))   // PLAIN_TEXT 만 핸들링
            .andDo(print())
            .andExpect(status().isNotAcceptable());


        this.mockMvc.perform(get("/helloJsonToTextResponse")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .accept(MediaType.TEXT_PLAIN))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("hello json text response"));

        // accept-header 제거 -> 요청 아무거나 받을거야. & 응답 text 줄거야. 매치 OK
        this.mockMvc.perform(get("/helloJsonToTextResponse")
                                 .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("hello json text response"));
    }

    @Test
    void helloJsonTest() throws Exception {
        // 컨텐츠 타입 XML을 consume 할 핸들러가 존재하지 않음. 415 Unsupported MediaType
        this.mockMvc.perform(get("/helloJson")
                                 .contentType(MediaType.APPLICATION_XML))
            .andDo(print())
            .andExpect(status().isUnsupportedMediaType());

        this.mockMvc.perform(get("/helloJson")
                                 .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("hello json"));

    }

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