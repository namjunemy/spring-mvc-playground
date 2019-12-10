package io.namjune.springmvcplayground.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.namjune.springmvcplayground.domain.Person;
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
import org.springframework.http.MediaType;
import org.springframework.oxm.Marshaller;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Marshaller marshaller;

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

    @Test
    @DisplayName("default message converter - string message converter")
    void stringMessageConvert() throws Exception {
        this.mockMvc.perform(get("/message")
                                 .content("nj"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("nj"));
    }

    @Test
    @DisplayName("json message test")
    void xmlMessageConvert() throws Exception {
        Person nj = new Person();
        nj.setId(1L);
        nj.setName("nj");
        this.mockMvc.perform(get("/objectMessage")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(nj)))
            .andDo(print())
            .andExpect(status().isOk())
            // json 객체의 응답은 jsonPath를 활용해서 검증할 수 있다.
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("nj"));
    }

    @Test
    @DisplayName("json message test")
    void jsonMessageConvert() throws Exception {
        Person nj = new Person();
        nj.setId(1L);
        nj.setName("nj");

        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);
        marshaller.marshal(nj, result);

        this.mockMvc.perform(get("/objectMessage")
                                 .contentType(MediaType.APPLICATION_XML)
                                 .accept(MediaType.APPLICATION_XML)
                                 .content(stringWriter.toString()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(xpath("person/id").string("1"))
            .andExpect(xpath("person/name").string("nj"));
    }
}