package com.tcc.test.configuration;

import com.tcc.test.model.enums.RoleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Usar HasAuthority!!
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(requests ->
        requests
//             requests.requestMatchers(HttpMethod.GET, "/person").hasAuthority(RoleEnum.ADMIN.toString())
//                 .requestMatchers(HttpMethod.POST, "/person/**").hasAuthority(RoleEnum.ADMIN.toString())
//                 .requestMatchers(HttpMethod.PUT, "/person/**").hasAuthority(RoleEnum.ADMIN.toString())
//
//                 .requestMatchers(HttpMethod.DELETE, "/person/**").hasAuthority(RoleEnum.ADMIN.toString())
//                 .requestMatchers(HttpMethod.DELETE, "/**").permitAll()
//                 .requestMatchers(HttpMethod.PUT, "/**").permitAll()
//                 .requestMatchers(HttpMethod.GET, "/**").permitAll()
//                 .requestMatchers(HttpMethod.POST, "/**").permitAll()

//        requests.requestMatchers(HttpMethod.GET, "/").authenticated()
//                .requestMatchers(HttpMethod.POST, "/").permitAll()
//                .requestMatchers(HttpMethod.PUT, "/").permitAll()
//                .requestMatchers(HttpMethod.DELETE, "/").permitAll()
//            .requestMatchers(HttpMethod.POST, "/person").hasAuthority(RoleEnum.ADMIN.toString())
//            .requestMatchers(HttpMethod.GET, "/person").hasAuthority(RoleEnum.ADMIN.toString())

                .anyRequest().permitAll()
    );

    http.csrf(AbstractHttpConfigurer::disable);
//    http.cors(withDefaults());
//    http.oauth2Login(withDefaults());
    http.httpBasic(withDefaults());
    http.formLogin(withDefaults());
    return http.build();

    /*
     being the hasRole() need not specify the ROLE prefix
     while the hasAuthority() needs the complete
     string to be explicitly specified. For instance,
     hasAuthority("ROLE_ADMIN") and hasRole("ADMIN")
     perform the same task

     */


    /*
     .formLogin()
      .loginPage("/login.html")
      .loginProcessingUrl("/perform_login")
      .defaultSuccessUrl("/homepage.html", true)
      .failureUrl("/login.html?error=true")
      .failureHandler(authenticationFailureHandler())
      .and()
      .logout()
      .logoutUrl("/perform_logout")
      .deleteCookies("JSESSIONID")
      .logoutSuccessHandler(logoutSuccessHandler());
      return http.build();
}
     */

  }
}
