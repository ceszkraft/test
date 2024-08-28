package com.tcc.test.service;

import com.github.dockerjava.api.exception.NotFoundException;
import com.tcc.test.model.dto.PersonDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.Random.class)
public class PersonServiceTest {
  private PersonDTO personDTO;
  @BeforeEach
  public void setup() {

    personDTO = new PersonDTO();
    personDTO.setId(UUID.fromString("582bc0f3-fce6-4f7f-af08-4910dee1662c"));
    personDTO.setBirthday(LocalDate.of(1992, 12, 7));
    personDTO.setEmail("brancocaesar@icloud.com");
    personDTO.setCpf("10284501417");
    personDTO.setFirstName("Caesar");
    personDTO.setLastName("Branco");
    // 123
    personDTO.setPassword("$2a$12$Un0KDsZ1gWC/1dzzRIHee.6b/QIgPBLqRMvnXj9FHtPl8OXe3Y1dG");
    personDTO.setUsername("REXbranco");

  }

  @DisplayName(value = "Save")
  @Transactional
  @Test
  public void shouldSaveANewObject() {

    PersonService serviceMock = mock(PersonService.class);
    when(serviceMock.save(personDTO)).thenReturn(personDTO);

    var saveObject = serviceMock.save(personDTO);

    assertThat(saveObject).isNotNull();
    assertThat(saveObject).isExactlyInstanceOf(PersonDTO.class);
    assertThat(saveObject).hasNoNullFieldsOrProperties();

  }

  @DisplayName(value = "FindAll()")
  @Test
  public void shouldReturnAListPersonObject() {

    PersonService serviceMock = mock(PersonService.class);
    when(serviceMock.findAll()).thenReturn(Collections.singletonList(personDTO));

    var saveObject = serviceMock.findAll();

    assertThat(saveObject).isNotNull();
    assertThat((long) saveObject.size()).isGreaterThanOrEqualTo(1);
    assertThat(saveObject.stream().map(PersonDTO::getId)).hasOnlyElementsOfType(UUID.class);

  }


  @DisplayName(value = "findById()")
  @Test
  public void shouldReturnPersonById() throws NotFoundException, ChangeSetPersister.NotFoundException {


    UUID id = UUID.fromString(String.valueOf(personDTO.getId()));
    PersonService serviceMock = mock(PersonService.class);
    when(serviceMock.findById(id)).thenReturn(personDTO);

    var saveObject = serviceMock.findById(id);

    assertThat(saveObject).isNotNull();
    assertThat(saveObject.getId()).isEqualTo(personDTO.getId());


  }
}
