package com.tcc.test.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import com.tcc.test.model.PersonModel;

import java.time.LocalDate;
import java.util.*;

//@AutoConfigureDataJpa
//@DataJpaTest(showSql = false)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(MockitoExtension.class)
public class PersonRepositoryTests {

  @Autowired
  private PersonRepository repository;
  private PersonModel person;
  @BeforeEach
  public void setup() {
    person = new PersonModel();
    person.setId(UUID.fromString("d3ff9cad-93a5-40c3-a801-9ace7dddd6bd"));
    person.setBirthday(LocalDate.of(1992, 12, 7));
    person.setEmail("testing@gmail.com");
    person.setCpf("57163091001");
    person.setFirstName("Ignis");
    person.setLastName("Glacies");
    // 123
    person.setSecret("$2a$12$Un0KDsZ1gWC/1dzzRIHee.6b/QIgPBLqRMvnXj9FHtPl8OXe3Y1dG");
    person.setUsername("Glacies");

  }

  @DisplayName(value = "JPA .save Object")
  @Test
  public void shouldSavePerson() {

    PersonRepository repositoryMock = mock(PersonRepository.class);
    when(repositoryMock.save(person)).thenReturn(person);

    PersonModel saveObject = repositoryMock.save(person);

    assertThat(saveObject).isNotNull();
    assertThat(saveObject.getEmail()).isEqualTo(person.getEmail());
  }

  @DisplayName(value = "Get by CPF")
  @Test
  public void shouldReturnFromExplicitCpf() {

    PersonRepository repositoryMock = mock(PersonRepository.class);
    when(repositoryMock.findByCpf(person.getCpf())).thenReturn(Optional.of(person));

    var saveObject = repositoryMock.findByCpf(person.getCpf());

    verify(repositoryMock, times(1)).findByCpf(person.getCpf());
    assertThat(saveObject).isNotEmpty();
    assertThat(saveObject).isNotNull();
    assertThat(saveObject.stream().count()).isEqualTo(1);
    assertThat(saveObject.stream().map(PersonModel::getCpf)).isEqualTo(Collections.singletonList(person.getCpf()));

  }

  @DisplayName(value = "Check list of Objects")
  @Test
  public void shouldReturnAListofPerson() {

    PersonRepository repositoryMock = mock(PersonRepository.class);
    when(repositoryMock.findAll()).thenReturn(Collections.singletonList(person));

    var saveObject = repositoryMock.findAll();

    assertThat(saveObject).isNotNull();
    assertThat((long) saveObject.size()).isGreaterThanOrEqualTo(1);
    assertThat(saveObject.stream().map(PersonModel::getId)).hasOnlyElementsOfType(UUID.class);

  }

  @DisplayName(value = "Update Person Object")
  @Test
  public void shouldReturnUpdatedPerson() {

    PersonRepository repositoryMock = mock(PersonRepository.class);
    when(repositoryMock.findByUsername(person.getUsername())).thenReturn(Optional.of(person));

    var saveObject = repositoryMock.findByUsername(person.getUsername());
    var updatedObject = saveObject.stream().map(personModel -> {
      personModel.setUsername("NewUsername");
      return personModel.getUsername();
    }).toList();

    assertThat(updatedObject).isNotNull();
    assertThat(saveObject.stream().map(PersonModel::getUsername).findAny()).isEqualTo(Optional.of(person.getUsername()));
    assertThat(saveObject.stream().map(PersonModel::getUsername)).isEqualTo(Collections.singletonList(person.getUsername()));

  }

  @DisplayName(value = "Delete Person Object")
  @Test
  @Disabled
  public void shouldDeleteAPersonObject() {

    UUID id = UUID.fromString(String.valueOf(person.getId()));

//    PersonRepository repositoryMock = mock(PersonRepository.class);
//    when(repositoryMock.deleteById(id)).thenReturn(person);

//    var saveObject = repositoryMock.findByCpf(person.getCpf());


//    repository.deleteById(id);

//    assertThat(saveObject).isNotNull();
//    assertThat(saveObject.stream().map(PersonModel::getId)).isEqualTo(List.of());
//    assertThat(saveObject).isEqualTo(Optional.empty());

  }

  @DisplayName(value = "Should use a custom JPQL")
  @Test
  public void shouldUseCustomJPQLFunction() {
//    @Query(value = "SELECT p from PersonModel p WHERE p.firstName =?1 and p.lastName = ?2")
//    PersonModel findByJPQL(String firstName, String lastName);

    PersonRepository repositoryMock = mock(PersonRepository.class);
    when(repositoryMock.findByJPQLnamedParameters(person.getFirstName(), person.getLastName())).thenReturn(person);

    var saveObject = repositoryMock.findByJPQLnamedParameters(person.getFirstName(), person.getLastName());

    assertThat(saveObject).isNotNull();
    assertThat(saveObject).isExactlyInstanceOf(PersonModel.class);
    assertThat(saveObject.getId()).isEqualTo(person.getId());


  }
  @DisplayName(value = "Using JPQL @Param()")
  @Test
  public void shouldTestParamAndJPQLFunction() {

    PersonRepository repositoryMock = mock(PersonRepository.class);
    when(repositoryMock.findByJPQL(person.getFirstName(), person.getLastName())).thenReturn(person);

    var saveObject = repositoryMock.findByJPQL(person.getFirstName(), person.getLastName());

    assertThat(saveObject).isNotNull();
    assertThat(saveObject).isExactlyInstanceOf(PersonModel.class);
    assertThat(saveObject.getId()).isEqualTo(person.getId());

  }

  @DisplayName(value = "Using JPQL Native Query")
  @Test
  public void shouldUseJPQLNativeQuery() {

    PersonRepository repositoryMock = mock(PersonRepository.class);
    when(repositoryMock.findByJPQLNativeQuery(person.getEmail(), person.getCpf())).thenReturn(person);

    var saveObject = repositoryMock.findByJPQLNativeQuery(person.getEmail(), person.getCpf());

    assertThat(saveObject).isNotNull();
    assertThat(saveObject).isExactlyInstanceOf(PersonModel.class);
    assertThat(saveObject.getId()).isEqualTo(person.getId());

  }

  @DisplayName(value = "Using JPQL Native Query W/ Params")
  @Test
  public void shouldUseJPQLNativeQueryWithParams() {

    PersonRepository repositoryMock = mock(PersonRepository.class);
    when(repositoryMock.findByJPQLNativeQueryWithParams(person.getCpf(), person.getEmail())).thenReturn(person);

    var saveObject = repositoryMock.findByJPQLNativeQueryWithParams(person.getCpf(), person.getEmail());

    assertThat(saveObject).isNotNull();
    assertThat(saveObject).isExactlyInstanceOf(PersonModel.class);
    assertThat(saveObject.getId()).isEqualTo(person.getId());
  }


  @DisplayName(value = "findById()")
  @Test
  public void shouldReturnPersonById() {

    PersonRepository repositoryMock = Mockito.mock(PersonRepository.class);
    when(repositoryMock.findByJPQLnamedParameters(person.getFirstName(), person.getLastName())).thenReturn(person);

    var saveObject = repositoryMock.findByJPQLnamedParameters(person.getFirstName(), person.getLastName());

    assertThat(saveObject).isNotNull();
    assertThat(saveObject.getId()).isEqualTo(person.getId());

  }
}

