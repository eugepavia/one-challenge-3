package com.eugepavia.challenge3.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDTO(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") double fechaNacimiento,
        @JsonAlias("death_year") double fechaFallecimiento
) {
}
