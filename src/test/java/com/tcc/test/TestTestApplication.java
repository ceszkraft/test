package com.tcc.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestTestApplication {


	public static void main(String[] args) {
		SpringApplication.from(TestApplication::main).with(TestTestApplication.class).run(args);
	}

}
