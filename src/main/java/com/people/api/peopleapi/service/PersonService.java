package com.people.api.peopleapi.service;

import java.util.List;
import java.util.stream.Collectors;
import com.people.api.peopleapi.dto.request.PersonDTO;
import com.people.api.peopleapi.dto.response.MessageResponseDTO;
import com.people.api.peopleapi.entity.Person;
import com.people.api.peopleapi.exception.PersonNotFoundException;
import com.people.api.peopleapi.mapper.PersonMapper;
import com.people.api.peopleapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public MessageResponseDTO generatePerson(PersonDTO personDTO) {
        Person personToSave = personMapper.toModel(personDTO);

        Person savedPerson = personRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Created person with the ID of: ");
    }

    public List<PersonDTO> listAll() {
        List<Person> allPersons = personRepository.findAll();
        return allPersons.stream().map(personMapper::toDTO).collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfItExists(id);
        return personMapper.toDTO(person);
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIfItExists(id);
        personRepository.deleteById(id);
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfItExists(id);
        Person personToUpdate = personMapper.toModel(personDTO);

        Person updatedPerson = personRepository.save(personToUpdate);
        return createMessageResponse(updatedPerson.getId(), "Updated the person with the ID of: ");
    }

    private Person verifyIfItExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
                    .builder()
                    .message(message + id)
                    .build();
    }
}
