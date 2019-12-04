package io.namjune.springmvcplayground.config;

import io.namjune.springmvcplayground.controller.request.Person;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class PersonFormatter implements Formatter<Person> {

    @Override
    public Person parse(String text, Locale locale) throws ParseException {
        return new Person(text);
    }

    @Override
    public String print(Person object, Locale locale) {
        return object.getName();
    }
}
