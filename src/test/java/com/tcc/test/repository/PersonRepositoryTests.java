package com.tcc.test.repository;


import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tcc.test.model.PersonModel;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

// O BANCO TEM QUE TA RODANDO
@AutoConfigureDataJpa
@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.Random.class)
public class PersonRepositoryTests {

  @Autowired
  private PersonRepository repository;

  private PersonModel person;

  @BeforeEach
  public void setup() {
    person = new PersonModel();
    person.setId(UUID.fromString("582bc0f3-fce6-4f7f-af08-4910dee1662c"));
    person.setBirthday(LocalDate.of(1992, 12, 7));
    person.setEmail("brancocaesar@icloud.com");
    person.setCpf("10284501417");
    person.setFirstName("Caesar");
    person.setLastName("Branco");
    // 123
    person.setSecret("$2a$12$Un0KDsZ1gWC/1dzzRIHee.6b/QIgPBLqRMvnXj9FHtPl8OXe3Y1dG");
    person.setUsername("REXbranco");

  }

  @DisplayName(value = "JPA .save Object")
  @Test
  public void shouldSavePersonInDatabase() {

    // when
    var savedPerson = repository.save(person);

    // then
    assertNotNull(savedPerson);
  }

  @DisplayName(value = "Get by CPF")
  @Test
  public void shouldReturnFromExplicitCpf() {

    // when
    repository.save(person);
    Optional<PersonModel> optionalPersonByCpf = repository.findByCpf("10284501417");

    // then
    Assertions.assertThat(optionalPersonByCpf).isNotEmpty();
    assertTrue(optionalPersonByCpf.isPresent());
  }

  @DisplayName(value = "Check list of Objects")
  @Test
  public void shouldReturnAListofPerson() {
    // given
    PersonModel person1 = new PersonModel();
    person1.setId(UUID.fromString("49f44ab8-f539-49c9-92af-02a591055902"));
    person1.setBirthday(LocalDate.of(2000, 12, 1));
    person1.setEmail("uberflussigkraftstoff@gmail.com");
    person1.setCpf("99284501417");
    person1.setFirstName("Henrique");
    person1.setLastName("Gonzaga");
    // 123
    person1.setSecret("$2a$12$Un0KDsZ1gWC/1dzzRIHee.6b/QIgPBLqRMvnXj9FHtPl8OXe3Y1dG");
    person1.setUsername("EmperorBranco");

    repository.save(person);
    repository.save(person1);
    // when

    List<PersonModel> personList = repository.findAll();
    // then

    assertNotNull(personList);
    Assertions.assertThat(personList.size()).isEqualTo(2);
  }

  @DisplayName(value = "Update Person Object")
  @Test
  public void shouldReturnUpdatedPerson() {

    // given
    PersonModel savedPerson = repository.save(person);

    String emailNotUpdated = person.getEmail();
    String email = "test@test.com";

    // when
    savedPerson.setEmail(email);

    // then
    Assertions.assertThat(savedPerson).isNotNull();
    Assertions.assertThat(savedPerson).isNotEqualTo(emailNotUpdated);
    Assertions.assertThat(savedPerson.getEmail()).isEqualTo(email);

  }

  @DisplayName(value = "Delete Person Object")
  @Test
  public void shouldDeleteAPersonObject() {

    PersonModel savedPerson = repository.save(person);

    // when
    repository.deleteById(savedPerson.getId());
    Optional<PersonModel> findObjectById = repository.findById(savedPerson.getId());

    // then
    Assertions.assertThat(findObjectById).isEmpty();
    Assertions.assertThat(findObjectById).isNotNull();

  }

  @DisplayName(value = "Should use a custom JPQL")
  @Test
  public void shouldUseCustomJPQLFunction() {
    repository.save(person);

    String firstName = "Caesar";
    String lastName = "Branco";

    // when

    PersonModel personByJPQL = repository.findByJPQL(firstName, lastName);

    // then

    Assertions.assertThat(personByJPQL).isNotNull();
    Assertions.assertThat(personByJPQL.getFirstName()).isEqualTo(firstName);
    Assertions.assertThat(personByJPQL.getLastName()).isEqualTo(lastName);

  }

  @DisplayName(value = "Using JPQL @Param()")
  @Test
  public void shouldTestParamAndJPQLFunction() {

    repository.save(person);

    String firstName = "Caesar";
    String lastName = "Branco";

    // when

    PersonModel personByJPQL = repository.findByJPQLnamedParameters(firstName, lastName);

    // then

    Assertions.assertThat(personByJPQL).isNotNull();
    Assertions.assertThat(personByJPQL.getFirstName()).isEqualTo(firstName);
    Assertions.assertThat(personByJPQL.getLastName()).isEqualTo(lastName);

  }

  @DisplayName(value = "Using JPQL Native Query")
  @Test
  public void shouldUseJPQLNativeQuery() {

    PersonModel person = new PersonModel();
    person.setId(UUID.fromString("582bc0f3-fce6-4f7f-af08-4910dee1662c"));
    person.setBirthday(LocalDate.of(1992, 12, 7));
    person.setEmail("brancocaesar@icloud.com");
    person.setCpf("10284501417");
    person.setFirstName("Caesar");
    person.setLastName("Branco");
    // 123
    person.setSecret("$2a$12$Un0KDsZ1gWC/1dzzRIHee.6b/QIgPBLqRMvnXj9FHtPl8OXe3Y1dG");
    person.setUsername("REXbranco");
    repository.save(person);

    String email = "brancocaesar@icloud.com";
    String cpf = "10284501417";

    // when
    PersonModel personByJPQL = repository.findByJPQLNativeQuery(email, cpf);

    // then
    Assertions.assertThat(personByJPQL).isNotNull();
    Assertions.assertThat(personByJPQL.getEmail()).isEqualTo(email);
    Assertions.assertThat(personByJPQL.getCpf()).isEqualTo(cpf);

  }

  @DisplayName(value = "Using JPQL Native Query W/ Params")
  @Test
  public void shouldUseJPQLNativeQueryWithParams() {

    repository.save(person);

    String email = "brancocaesar@icloud.com";
    String cpf = "10284501417";

    // when
    PersonModel personByJPQL = repository.findByJPQLNativeQueryWithParams(cpf, email);

    // then
    Assertions.assertThat(personByJPQL).isNotNull();
    Assertions.assertThat(personByJPQL.getEmail()).isEqualTo(email);
    Assertions.assertThat(personByJPQL.getCpf()).isEqualTo(cpf);

  }

}

