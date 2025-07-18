package com.example.start_spring_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StartSpringAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartSpringAppApplication.class, args);
	}

}
