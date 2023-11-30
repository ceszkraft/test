package com.tcc.test.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

  public void addCorsMappings(CorsRegistry registry) {
//    registry.addMapping("/**")  funciona se allowCredentials(false)
    registry.addMapping("http://localhost:4200/")
        .allowCredentials(true)
        .allowedOrigins("http://localhost:4200/register")
        .allowedHeaders(
            HttpHeaders.AUTHORIZATION,
            HttpHeaders.CONTENT_TYPE,
            HttpHeaders.ACCEPT)
        .allowedMethods(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name()
        )
        .maxAge(3600L);
  }


}
