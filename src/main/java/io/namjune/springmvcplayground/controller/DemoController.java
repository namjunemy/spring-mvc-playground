package io.namjune.springmvcplayground.controller;

import io.namjune.springmvcplayground.config.GetHelloMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @GetMapping(value = "/helloParam", params = "name!=hacker")
    @ResponseBody
    public String helloParam(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping(value = "/helloHeader", headers = HttpHeaders.FROM)
    @ResponseBody
    public String helloHeader() {
        return "hello header";
    }

    @GetMapping(
        value = "/helloJsonToTextResponse",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ResponseBody
    public String helloJsonToTextResponse() {
        return "hello json text response";
    }

    @GetMapping(value = "/helloJson", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String helloJson() {
        return "hello json";
    }

    @PostMapping("/hello")
    @ResponseBody
    public String helloPost() {
        return "hello";
    }

    @GetHelloMapping
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @GetMapping({"/hello/?", "/hello/*", "/hello/**"})
    @ResponseBody
    public String helloUri() {
        return "hello uri";
    }
}
