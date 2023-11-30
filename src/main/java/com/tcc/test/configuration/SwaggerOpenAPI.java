package com.tcc.test.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerOpenAPI {

  // http://localhost:8080/swagger-ui/index.html#/
  @Bean
  OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Swagger API")
            .version("v1")
            .description("TCC - API tests")
            .termsOfService("")
            .license(new License().name("Apache 2.0")
                .url("http://localhost:8080/swagger")));
  }
}
