package com.tcc.test.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "tb_person")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonModel implements UserDetails{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "person_id")
  private UUID id;

  @Column(length = 11, nullable = false)
  private String cpf;

  @Column(name = "primeiro_nome", length = 60, nullable = false)
  private String firstName;

  @Column(name = "ultimo_nome", length = 60, nullable = false)
  private String lastName;

  @Column(name = "data_nascimento", length = 10, nullable = false)
  private LocalDate birthday;

  @Column(length = 100, nullable = false, unique = true)
  private String email;

  @Column(name = "senha", length = 60, nullable = false)
  private String secret;

  @Column(length = 30, nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private boolean isAccountNonExpired;

  @Column(nullable = false)
  private boolean isAccountNonLocked;

  @Column(nullable = false)
  private boolean isCredentialsNonExpired;

  @Column(nullable = false)
  private boolean isEnabled;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "TB_JOINED_PERSON_ROLE",
      joinColumns = @JoinColumn(name = "person_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private List<RoleModel> roles;

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles;
  }

  @Override
  public String getPassword() {
    return this.secret;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.isAccountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.isAccountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isCredentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }
}
