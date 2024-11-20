package com.eugepavia.challenge3.repository;

import com.eugepavia.challenge3.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

// Interfaz para Derived y Native Queries en la base de datos Postgres
// Utiliza la dependencia Spring Data JPA

public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Revisa si un autor ya ha sido registrado
    Optional<Autor> findByNombreEquals(String nombreAutor);

    @Query("SELECT a FROM Autor a WHERE a.nombre ILIKE %:nombreAutor%")
    Autor buscaAutorPorNombre(String nombreAutor);

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anio AND a.fechaFallecimiento >= :anio")
    List<Autor> buscaAutorPorAnio(Double anio);

    @Query("SELECT a FROM Autor a WHERE a.nombre ILIKE %:nombre%")
    List<Autor> buscaAutorDB(String nombre);


}
