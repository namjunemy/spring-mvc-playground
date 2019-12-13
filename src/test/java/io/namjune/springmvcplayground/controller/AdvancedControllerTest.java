package io.namjune.springmvcplayground.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class AdvancedControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getEvent() throws Exception {
        this.mockMvc.perform(get("/events/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(1));

        this.mockMvc.perform(get("/events/1;name=nj"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(1));
    }
}