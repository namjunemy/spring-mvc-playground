package io.namjune.springmvcplayground.controller;

import io.namjune.springmvcplayground.domain.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {

    @GetMapping("/hello/user")
    public String helloIdPath(@RequestParam(value = "id") Person person) {
        return "hello " + person.getName() + "(id : " + person.getId() + ")";
    }

    @GetMapping("/message")
    public String message(@RequestBody String body) {
        return body;
    }

    @GetMapping("/objectMessage")
    public Person message(@RequestBody Person person) {
        return person;
    }
}
