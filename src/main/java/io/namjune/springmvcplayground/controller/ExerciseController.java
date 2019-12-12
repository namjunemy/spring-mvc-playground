package io.namjune.springmvcplayground.controller;

import io.namjune.springmvcplayground.domain.Person;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ExerciseController {

    @GetMapping({"/events", "/events/{code}"})
    @ResponseBody
    public String find(@PathVariable(required = false) String code) {
        if (code == null) {
            code = Strings.EMPTY;
        }
        return "events" + code;
    }

    @PostMapping(
        value = "/events",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Person create() {
        return new Person();
    }

    @DeleteMapping("/events/{code}")
    @ResponseBody
    public String delete(@PathVariable String code) {
        return code;
    }

    @PutMapping(
        value = "/events/{code}",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String update(@PathVariable String code) {
        return code;
    }
}
