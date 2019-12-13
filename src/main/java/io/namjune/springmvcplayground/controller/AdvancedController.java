package io.namjune.springmvcplayground.controller;

import io.namjune.springmvcplayground.domain.Event;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdvancedController {

    @GetMapping("/events/{id}")
    @ResponseBody
    public Event getEvent(@PathVariable Long id, @MatrixVariable(required = false) String name) {
        Event event = new Event();
        event.setId(id);
        if (!StringUtils.isEmpty(name)) {
            event.setName(name);
        }
        return event;
    }
}
