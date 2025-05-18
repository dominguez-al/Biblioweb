package com.biblioweb.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BibliowebBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliowebBackendApplication.class, args);
	}

}
