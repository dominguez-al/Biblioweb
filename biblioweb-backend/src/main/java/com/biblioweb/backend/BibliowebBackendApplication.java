package com.biblioweb.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

//Anotación que marca esta clase como la clase principal de una aplicación Spring Boot
@SpringBootApplication

//Habilita el soporte para la ejecución asíncrona de métodos con @Async
@EnableAsync
public class BibliowebBackendApplication {

 // Método principal que arranca la aplicación Spring Boot
 public static void main(String[] args) {
     SpringApplication.run(BibliowebBackendApplication.class, args);
 }

}
