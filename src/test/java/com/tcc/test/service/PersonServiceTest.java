package com.tcc.test.service;

import com.github.dockerjava.api.exception.NotFoundException;
import com.tcc.test.model.dto.PersonDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tcc.test.model.PersonModel;
import com.tcc.test.repository.PersonRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PersonServiceTest {

  @Mock
  private PersonRepository repository;

  private PersonModel personModel;

  private PersonDTO personDTO;

  @BeforeEach
  public void setup() {

    personModel = new PersonModel();
    personModel.setId(UUID.fromString("582bc0f3-fce6-4f7f-af08-4910dee1662c"));
    personModel.setBirthday(LocalDate.of(1992, 12, 7));
    personModel.setEmail("brancocaesar@icloud.com");
    personModel.setCpf("10284501417");
    personModel.setFirstName("Caesar");
    personModel.setLastName("Branco");
    // 123
    personModel.setSecret("$2a$12$Un0KDsZ1gWC/1dzzRIHee.6b/QIgPBLqRMvnXj9FHtPl8OXe3Y1dG");
    personModel.setUsername("REXbranco");


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
    Mockito.when(this.repository.findById(UUID.fromString("582bc0f3-fce6-4f7f-af08-4910dee1662c"))).thenReturn(Optional.of(personModel));

    Assertions.assertThat(this.repository.findById(UUID.fromString("582bc0f3-fce6-4f7f-af08-4910dee1662c"))).isNotEmpty();
    Assertions.assertThat(this.repository.findById(UUID.fromString("582bc0f3-fce6-4f7f-af08-4910dee1662c"))).isNotEmpty();

  }

  @DisplayName(value = "FindAll()")
  @Transactional
  @Test
  public void shouldReturnAListPersonObject() {
    // given
    Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(personModel));

    // when
    var save = this.repository.findAll();

    // then
    Assertions.assertThat(save).isNotEmpty();
    Assertions.assertThat(save).hasSize(1);
    Assertions.assertThat(save.contains(personModel)).isTrue();

  }


  @DisplayName(value = "findById()")
  @Test
  public void shouldReturnPersonById() throws NotFoundException, ChangeSetPersister.NotFoundException {

    UUID id = UUID.fromString("c27aeae4-cbf1-405b-8578-0af0fa5ef3e1");
    String number = "10284501417";

    PersonService serviceMock = Mockito.mock(PersonService.class);

    Mockito.when(serviceMock.findById(id)).thenReturn(personDTO);

    var personMock = serviceMock.findById(id);

    Assertions.assertThat(personMock).isNotNull();
    Assertions.assertThat(personMock.getCpf()).isEqualTo(number);

  }
}
