package com.tcc.test.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tcc.test.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcc.test.model.PersonModel;
import com.tcc.test.model.dto.PersonDTO;
import com.tcc.test.repository.PersonRepository;
import com.tcc.test.util.MessagesUtil;
import com.tcc.test.util.exception.BusinessException;

@Service
public class PersonService {
  @Autowired
  private PersonRepository repository;

  @Autowired
  private PersonMapper mapper;


  @Transactional
  public PersonDTO save(PersonDTO dto) {

    Optional<PersonModel> optionalPersonModel = repository.findByUsername(dto.getUsername());
    if (optionalPersonModel.isPresent()) {
      throw new BusinessException(MessagesUtil.USER_ALREADY_REGISTERED);
    }
    PersonModel personModel = mapper.toEntity(dto);
    repository.save(personModel);
    return mapper.toDTO(personModel);

  }

  @Transactional(readOnly = true)
  public List<PersonDTO> findAll() {
    return mapper.toDTO(repository.findAll());
  }

  @Transactional(readOnly = true)
  public PersonDTO findById(UUID id) throws NotFoundException {
    return repository.findById(id).map(mapper::toDTO)
        .orElseThrow(NotFoundException::new);
  }

  @Transactional
  public PersonDTO delete(UUID id) throws NotFoundException {
    PersonDTO dto = this.findById(id);
    this.repository.deleteById(dto.getId());
    return dto;
  }

  @Transactional
  public PersonDTO update(PersonDTO dto) {

    Optional<PersonModel> optionalPessoa = repository.findByUsername(dto.getUsername());
    if (optionalPessoa.isPresent()) {
      throw new BusinessException(MessagesUtil.USER_ALREADY_REGISTERED);
    }

    PersonModel pessoa = mapper.toEntity(dto);
    repository.save(pessoa);
    return mapper.toDTO(pessoa);
  }
}
