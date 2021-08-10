package com.people.api.peopleapi.repository;

import com.people.api.peopleapi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    
}
