package com.example.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@ComponentScan
@EnableJpaRepositories("com.example.bank.repository")
@SpringBootApplication(scanBasePackages = {"com.example.bank.controllers.AccountController",
		"com.example.bank.controllers.TransactionController"})
public class SpringEmbeddedH2DbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEmbeddedH2DbApplication.class, args);
	}

}
