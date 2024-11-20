package com.eugepavia.challenge3;

import com.eugepavia.challenge3.principal.Principal;
import com.eugepavia.challenge3.repository.AutorRepository;
import com.eugepavia.challenge3.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Challenge3Application implements CommandLineRunner {

	@Autowired
	LibroRepository libroRepository;

	@Autowired
	AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(Challenge3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository,autorRepository);
		principal.muestraMenu();
	}

}
