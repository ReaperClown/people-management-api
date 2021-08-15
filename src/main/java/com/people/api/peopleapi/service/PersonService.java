package com.people.api.peopleapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.people.api.peopleapi.dto.request.PersonDTO;
import com.people.api.peopleapi.dto.response.MessageResponseDTO;
import com.people.api.peopleapi.entity.Person;
import com.people.api.peopleapi.exception.PersonNotFoundException;
import com.people.api.peopleapi.mapper.PersonMapper;
import com.people.api.peopleapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO generatePerson(PersonDTO personDTO) {
        Person personToSave = personMapper.toModel(personDTO);

        Person savedPerson = personRepository.save(personToSave);

        return MessageResponseDTO.builder().message("Created person with the ID of: " + savedPerson.getId()).build();
    }

    public List<PersonDTO> listAll() {
        List<Person> allPersons = personRepository.findAll();
        return allPersons.stream().map(personMapper::toDTO).collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        
        return personMapper.toDTO(person);
    }
}
