package com.eugepavia.challenge3;

import com.eugepavia.challenge3.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Challenge3Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Challenge3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraMenu();
	}

}
