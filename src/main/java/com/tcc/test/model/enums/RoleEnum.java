package com.tcc.test.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum RoleEnum {
  ADMIN("ROLE_ADMIN"),
  USER("ROLE_USER");

  private final String desc;
}
