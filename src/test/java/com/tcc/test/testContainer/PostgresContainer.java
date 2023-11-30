package com.tcc.test.testContainer;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class PostgresContainer {

  private static final String POSTGRES_DATABASE_NAME = "tcc";
  private static final String POSTGRES_IMAGE_NAME = "postgres:15.4-alpine3.18";
  private static final String POSTGRES_DATABASE_CREDENTIALS = "postgres";
  private static final Integer POSTGRES_PORT = 5432;

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(POSTGRES_IMAGE_NAME)
      .withDatabaseName(POSTGRES_DATABASE_NAME)
      .withUsername(POSTGRES_DATABASE_CREDENTIALS)
      .withPassword(POSTGRES_DATABASE_CREDENTIALS)
      .withExposedPorts(POSTGRES_PORT);

  @DisplayName(value = "Should Create a container and check if its RUNNING")
  @Test
  public void shouldCreateAContainerAndVerifyIfItsRunning() {

    Assertions.assertThat(postgres.isCreated()).isTrue();
    Assertions.assertThat(postgres.isRunning()).isTrue();
  }

  @DisplayName(value = "Should Validate Username & Password")
  @Test
  public void shouldValidateCredentials() {
    Assertions.assertThat(postgres.getUsername()).isEqualTo("postgres");
    Assertions.assertThat(postgres.getPassword()).isEqualTo("postgres");
  }

  @DisplayName(value = "Should Validate PORT")
  @Test
  public void shouldValidatePort() {
    Assertions.assertThat(postgres.getExposedPorts()).isEqualTo(List.of(5432));
  }

  @DisplayName(value = "Should Validate Postgres version")
  @Test
  public void shouldValidateVersion() {
    Assertions.assertThat(postgres.getDockerImageName()).isEqualTo("postgres:15.4-alpine3.18");
  }

  @DisplayName(value = "Should Validate Postgres version")
  @Test
  public void shouldValidateDatabaseName() {
    Assertions.assertThat(postgres.getDatabaseName()).isEqualTo("tcc");
  }

}
