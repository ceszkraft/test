package com.tcc.test.service.security;

import com.tcc.test.model.PersonModel;
import com.tcc.test.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class LoadUserBySecurity implements UserDetailsService{

  @Autowired
  private PersonRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    PersonModel userModel = repository.findByUsername(username).orElseThrow(
        () -> new UsernameNotFoundException(username + " não encontrado."));

    return new User(
        userModel.getUsername(),
        userModel.getPassword(),
        userModel.isEnabled(),
        userModel.isAccountNonExpired(),
        userModel.isCredentialsNonExpired(),
        userModel.isAccountNonLocked(),
        userModel.getAuthorities()
    );
  }
}
