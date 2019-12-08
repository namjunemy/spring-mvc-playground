package io.namjune.springmvcplayground.controller;

import io.namjune.springmvcplayground.controller.request.Person;
import io.namjune.springmvcplayground.repository.PersonRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Test
    @DisplayName("hello id use @Entity")
    @Order(1)
    void helloIdPath() throws Exception {
        Person nj = new Person();
        nj.setName("nj");
        Person savedPerson = this.personRepository.save(nj);

        this.mockMvc.perform(get("/hello/user")
                                 .param("id", savedPerson.getId().toString()))
            .andDo(print())
            .andExpect(content().string("hello nj(id : 1)"));
    }

    @Test
    @DisplayName("static resource handler test")
    void helloStaticResource() throws Exception {
        this.mockMvc.perform(get("/index.html"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(Matchers.containsString("hello index")));
    }

    @Test
    @DisplayName("static resource handler test")
    void helloStaticMobileResource() throws Exception {
        this.mockMvc.perform(get("/mobile/index.html"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(Matchers.containsString("hello mobile index")))
            .andExpect(header().exists(HttpHeaders.CACHE_CONTROL));
    }
}