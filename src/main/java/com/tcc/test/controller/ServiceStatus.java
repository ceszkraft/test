package com.tcc.test.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status")
@CrossOrigin(origins = "*")
public class ServiceStatus {

  @GetMapping()
  public String status(){
    return "true";
  }
}
