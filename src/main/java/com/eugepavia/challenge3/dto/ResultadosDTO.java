package com.eugepavia.challenge3.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// Clase DTO con los resultados de libros de la consulta a la API
// Utiliza la dependencia Jackson-Databind

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadosDTO(
        @JsonAlias("results") List<LibroDTO> resultados
        ) {
}
