package com.eugepavia.challenge3.model;

import com.eugepavia.challenge3.dto.LibroDTO;
import jakarta.persistence.*;

import java.util.Optional;

// Clase modelo para los objetos Libro
// En caso de múltiples actores e idiomas, se considera solo al primero del registro

@Entity
@Table(name = "Libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    @Enumerated(value = EnumType.STRING)
    private Idioma idioma;
    private double descargas;

    // CONSTRUCTORES
    public Libro () {}
    public Libro(LibroDTO dto) {
        Optional<Autor> resultado = dto.autor().stream()
                .map(a -> new Autor(a))
                .findFirst();
        if (resultado.isPresent()) {
            this.autor = resultado.get();
        } else {
            this.autor = new Autor("Desconocido",-100000,-100000);
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

    // SETTERS
    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
