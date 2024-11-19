package com.eugepavia.challenge3.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// Clase DTO con la información de un libro. Se extrae de ResultadosDTO
// Utiliza la dependencia Jackson-Databind

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDTO(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<AutorDTO> autor,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") double descargas
) {
}
