package com.eugepavia.challenge3.model;

import com.eugepavia.challenge3.dto.LibroDTO;

import java.util.Optional;

// Clase modelo para los objetos Libro
// En caso de múltiples actores e idiomas, se considera solo al primero del registro

public class Libro {
    private String titulo;
    private Autor autor;
    private Idioma idioma;
    private double descargas;

    public Libro(LibroDTO dto) {
        Optional<Autor> resultado = dto.autor().stream()
                .map(a -> new Autor(a))
                .findFirst();
        if (resultado.isPresent()) {
            this.autor = resultado.get();
        } else {
            this.autor = new Autor("Desconocido",0,0);
        }

        this.titulo = dto.titulo();
        this.idioma = Idioma.leeClave(dto.idioma().toString().substring(1,3));
        this.descargas = dto.descargas();
    }


    @Override
    public String toString() {
        String texto = """
                ---------------------------------------
                Título: %s
                Autor: %s
                Idioma: %s
                Total de descargas: %.0f
                ---------------------------------------""".formatted(titulo,autor.getNombre(),Idioma.devuelveEspanol(idioma),descargas);
        return texto;
    }

    // GETTERS
    public String getTitulo() {
        return titulo;
    }
    public Autor getAutor() {
        return autor;
    }
    public Idioma getIdioma() {
        return idioma;
    }
    public double getDescargas() {
        return descargas;
    }
}
