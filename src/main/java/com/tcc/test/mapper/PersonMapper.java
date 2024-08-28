package com.tcc.test.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tcc.test.model.PersonModel;
import com.tcc.test.model.dto.PersonDTO;


@Component
public class PersonMapper {


    public PersonMapper() {
   }

   public PersonModel toEntity(PersonDTO dto) {
      PersonModel personModel = new PersonModel();
      personModel.setId(dto.getId());
      personModel.setFullName(dto.getFullName());
      personModel.setCpf(dto.getCpf());
      personModel.setBirthday(dto.getBirthday());
      personModel.setEmail(dto.getEmail());
      personModel.setSecret(dto.getPassword());
      personModel.setUsername(dto.getUsername());
      return personModel;
   }

   public PersonDTO toDTO(PersonModel PersonModel) {
      PersonDTO dto = new PersonDTO();
      dto.setId(PersonModel.getId());
      dto.setFullName(PersonModel.getFullName());
      dto.setCpf(PersonModel.getCpf());
      dto.setEmail(PersonModel.getEmail());
      dto.setBirthday(PersonModel.getBirthday());
      dto.setPassword(PersonModel.getSecret());
      dto.setUsername(PersonModel.getUsername());
      return dto;
   }

   public List<PersonDTO> toDTO(List<PersonModel> listPersonModel) {
      return listPersonModel != null ? listPersonModel.stream().map(this::toDTO).collect(Collectors.toList()) : null;
   }

   public List<PersonModel> toEntity(List<PersonDTO> listPersonModel) {
      return listPersonModel != null ? listPersonModel.stream().map(this::toEntity).collect(Collectors.toList()) : null;
   }
}