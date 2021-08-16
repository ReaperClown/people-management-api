package com.people.api.peopleapi.service;

import com.people.api.peopleapi.dto.request.PersonDTO;
import com.people.api.peopleapi.dto.response.MessageResponseDTO;
import com.people.api.peopleapi.entity.Person;
import com.people.api.peopleapi.repository.PersonRepository;
import com.people.api.peopleapi.utils.PersonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.people.api.peopleapi.utils.PersonUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void testGivenPersonDTOThenReturnSavedMessage() {
        PersonDTO personDTO = createFakeDTO();
        Person expectedPerson = createFakeEntity();

        when(personRepository.save(any(Person.class))).thenReturn(expectedPerson);

        MessageResponseDTO expectedSuccessMessage = createExpectedMessageResponse(expectedPerson.getId());

        MessageResponseDTO successMessage = personService.generatePerson(personDTO);

        assertEquals(expectedSuccessMessage, successMessage);
    }

    private MessageResponseDTO createExpectedMessageResponse(Long id) {
        return MessageResponseDTO
                .builder()
                .message("Created person with the ID of: " + id)
                .build();
    }
}
