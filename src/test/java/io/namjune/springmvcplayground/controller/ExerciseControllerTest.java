package io.namjune.springmvcplayground.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class ExerciseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("find")
    void first() throws Exception {
        this.mockMvc.perform(get("/events"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("events"));

        this.mockMvc.perform(get("/events/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("events1"));

        this.mockMvc.perform(get("/events/2"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("events2"));

        this.mockMvc.perform(get("/events/3"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("events3"));
    }

    @Test
    @DisplayName("update")
    void second() throws Exception {
        this.mockMvc.perform(post("/events")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());

    }

    @Test
    @DisplayName("delete")
    void third() throws Exception {
        this.mockMvc.perform(delete("/events/1"))
            .andDo(print())
            .andExpect(status().isOk());

        this.mockMvc.perform(delete("/events/2"))
            .andDo(print())
            .andExpect(status().isOk());

        this.mockMvc.perform(delete("/events/3"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("update")
    void fourth() throws Exception {
        this.mockMvc.perform(put("/events/1")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());

        this.mockMvc.perform(put("/events/2")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }
}