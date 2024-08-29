package com.tcc.test.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.test.model.PersonModel;
import com.tcc.test.model.dto.PersonDTO;
import com.tcc.test.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(value = PersonController.class)
@AutoConfigureMockMvc(addFilters = false)  // disable security
@ExtendWith(MockitoExtension.class)
public class PersonControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonService personService;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ObjectMapper objectMapper;

  private PersonModel person;


  @BeforeEach
  public void setup() {
    person = new PersonModel();
    person.setId(UUID.fromString("d3ff9cad-93a5-40c3-a801-9ace7dddd6bd"));
    person.setBirthday(LocalDate.of(1992, 12, 7));
    person.setEmail("testing@gmail.com");
    person.setCpf("57163091001");
    person.setFullName("Glacies Test");
    // 123
    person.setSecret("$2a$12$Un0KDsZ1gWC/1dzzRIHee.6b/QIgPBLqRMvnXj9FHtPl8OXe3Y1dG");
    person.setUsername("Glacies");

  }

  @Test
  @WithMockUser
  void testSavePerson() throws Exception {
    PersonDTO personDTO = new PersonDTO();
    personDTO.setFullName("Branco Caesar");
    personDTO.setId(null);
    personDTO.setEmail("test@example.com");
    personDTO.setCpf("60785513027");
    personDTO.setPassword("password");
    personDTO.setBirthday(LocalDate.of(1992, 12, 7));

    when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
    when(personService.save(any(PersonDTO.class))).thenReturn(personDTO);

    mockMvc.perform(post("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(personDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.email").value("test@example.com"))
        .andExpect(jsonPath("$.cpf").value("60785513027"))
        .andExpect(jsonPath("$.password").value("password"));
  }

  @Test
  @WithMockUser(username = "testing@gmail.com", roles = "ADMIN")
  void testFindAllPersons() throws Exception {
    // Criação de uma lista de PersonDTOs simulada
    List<PersonDTO> personDTOList = new ArrayList<>();
    PersonDTO person1 = new PersonDTO();
    person1.setId(UUID.randomUUID());
    person1.setEmail("test1@example.com");
    person1.setCpf("12345678900");
    person1.setFullName("Test User 1");

    PersonDTO person2 = new PersonDTO();
    person2.setId(UUID.randomUUID());
    person2.setEmail("test2@example.com");
    person2.setCpf("09876543210");
    person2.setFullName("Test User 2");

    personDTOList.add(person1);
    personDTOList.add(person2);

    // Mock do serviço para retornar a lista simulada
    when(personService.findAll()).thenReturn(personDTOList);

    // Execução da requisição GET ao endpoint /person e verificação da resposta
    mockMvc.perform(get("/person")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].email").value("test1@example.com"))
        .andExpect(jsonPath("$[0].cpf").value("12345678900"))
        .andExpect(jsonPath("$[0].fullName").value("Test User 1"))
        .andExpect(jsonPath("$[1].email").value("test2@example.com"))
        .andExpect(jsonPath("$[1].cpf").value("09876543210"))
        .andExpect(jsonPath("$[1].fullName").value("Test User 2"));
  }
}
