package com.traumaticevolutions.tevosales_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal para arrancar la aplicación Spring Boot.
 * 
 * @author Ángel Aragón
 */
@SpringBootApplication
public class TevosalesBackendApplication {

	/**
	 * Método principal que inicia la aplicación.
	 * 
	 * @param args Argumentos de línea de comandos (no utilizados).
	 */
	public static void main(String[] args) {
		SpringApplication.run(TevosalesBackendApplication.class, args);
	}

}
