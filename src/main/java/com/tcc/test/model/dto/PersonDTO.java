package com.tcc.test.model.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

  private UUID id;

  @NotNull(message = "First name must be filled")
  @NotBlank(message = "First name cannot be blank")
  private String firstName;

  @NotNull(message = "Last name must be filled")
  @NotBlank(message = "Last name cannot be blank")
  private String lastName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
  private LocalDate birthday;

  @Email
  private String email;

  @CPF
  private String cpf;

  private String password;

  private String username;

}
