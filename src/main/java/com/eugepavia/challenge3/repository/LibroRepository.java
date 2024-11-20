package com.eugepavia.challenge3.repository;

import com.eugepavia.challenge3.model.Idioma;
import com.eugepavia.challenge3.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

// Interfaz para Derived y Native Queries en la base de datos Postgres
// Utiliza la dependencia Spring Data JPA

public interface LibroRepository extends JpaRepository<Libro,Long> {

    // Revisa si un libro ya ha sido registrado
    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);

    @Query("SELECT l FROM Libro l WHERE l.idioma = :clave")
    List<Libro> buscaLibroPorIdioma(Idioma clave);

    @Query("SELECT l FROM Libro l WHERE l.titulo ILIKE %:titulo%")
    List<Libro> buscaLibroDB(String titulo);

    @Query("SELECT l FROM Libro l ORDER BY l.descargas DESC LIMIT 10")
    List<Libro> top10Libros();

}
