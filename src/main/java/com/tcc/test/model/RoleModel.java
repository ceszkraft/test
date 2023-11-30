package com.tcc.test.model;


import com.tcc.test.model.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import java.util.UUID;


@Entity
@Table(name = "tb_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleModel implements GrantedAuthority{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "person_id")
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name = "role_name",length = 5, nullable = false, unique = true)
  private RoleEnum roleName;

  @Override
  public String getAuthority() {
    return this.roleName.toString();
  }


}
