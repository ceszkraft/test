package com.tcc.test.repository;

import com.tcc.test.model.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonModel, UUID> {

  Optional<PersonModel> findByUsername(String findByUsername);

  Optional<PersonModel> findByCpf(String cpf);

  @Query(value = "SELECT p from PersonModel p WHERE p.firstName =?1 and p.lastName = ?2")
  PersonModel findByJPQL(String firstName, String lastName);

  @Query(value = "SELECT p from PersonModel p WHERE p.firstName =:firstName and p.lastName =:lastName")
  PersonModel findByJPQLnamedParameters(
      @Param("firstName") String firstName,
      @Param("lastName") String lastName);

  @Query(value = "SELECT * FROM tb_person p WHERE p.email=?1 and p.cpf=?2", nativeQuery = true)
  PersonModel findByJPQLNativeQuery(String email, String cpf);

  @Query(value = "SELECT * FROM tb_person p WHERE p.cpf =:cpf and p.email =:email",
      nativeQuery = true)
  PersonModel findByJPQLNativeQueryWithParams(
      @Param("cpf") String cpf,
      @Param("email") String email);


}
