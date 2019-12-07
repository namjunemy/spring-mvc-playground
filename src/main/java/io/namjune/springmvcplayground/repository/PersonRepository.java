package io.namjune.springmvcplayground.repository;

import io.namjune.springmvcplayground.controller.request.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
