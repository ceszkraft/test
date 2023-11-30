package com.tcc.test.controller;

import java.util.List;
import java.util.UUID;

import com.tcc.test.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.tcc.test.model.dto.PersonDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "*")
public class PersonController {
  @Autowired
  private PersonService service;

  @Autowired
  private PasswordEncoder encoder;

 @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<PersonDTO> save(@Valid @RequestBody PersonDTO personDTO){
   personDTO.setPassword(encoder.encode(personDTO.getPassword()));
   return ResponseEntity.status(HttpStatus.CREATED).body(service.save(personDTO));
 }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PersonDTO> update(@Valid @RequestBody PersonDTO dto){
    return ResponseEntity.ok(service.update(dto));
  }

 @GetMapping(produces = { "application/json" })
 public ResponseEntity<List<PersonDTO>> findAll() {
   return ResponseEntity.ok(this.service.findAll());
 }

 @GetMapping(value = { "/{id}" }, produces = { "application/json" })
 public ResponseEntity<PersonDTO> findById(@PathVariable UUID id) throws NotFoundException {
   return ResponseEntity.ok(this.service.findById(id));
 }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PersonDTO> delete(@PathVariable UUID id) throws NotFoundException {
    return ResponseEntity.ok(service.delete(id));
  }
}
