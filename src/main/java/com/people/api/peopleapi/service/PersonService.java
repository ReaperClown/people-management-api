package com.people.api.peopleapi.service;

import com.people.api.peopleapi.dto.MessageResponseDTO;
import com.people.api.peopleapi.entity.Person;
import com.people.api.peopleapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO generatePerson(Person person) {
        Person savedPerson = personRepository.save(person);

        return MessageResponseDTO
                        .builder()
                        .message("Created person with the ID of: " + savedPerson.getId())
                        .build();
    }
}
