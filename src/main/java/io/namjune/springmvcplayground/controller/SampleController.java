package io.namjune.springmvcplayground.controller;

import io.namjune.springmvcplayground.controller.request.Person;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/hello/{name}")
    public String helloNamePath(@PathVariable("name") Person person) {
        return "hello " + person.getName();
    }

    @GetMapping("/hello")
    public String helloNameParam(@RequestParam(value = "name", required = false) Person person) {
        if (ObjectUtils.isEmpty(person)) {
            person = new Person("");
        }
        return "hello " + person.getName();
    }
}
