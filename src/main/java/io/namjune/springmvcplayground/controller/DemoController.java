package io.namjune.springmvcplayground.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

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

    @GetMapping("/hello")
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
