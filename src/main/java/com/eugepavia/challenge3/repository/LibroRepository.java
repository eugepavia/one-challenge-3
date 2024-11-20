package com.eugepavia.challenge3.repository;

import com.eugepavia.challenge3.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Interfaz para Derived y Native Queries en la base de datos Postgres
// Utiliza la dependencia Spring Data JPA

public interface LibroRepository extends JpaRepository<Libro,Long> {

    // Revisa si un libro ya ha sido registrado
    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);


}
