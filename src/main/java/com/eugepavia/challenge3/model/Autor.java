package com.eugepavia.challenge3.model;

import com.eugepavia.challenge3.dto.AutorDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// Clase modelo para los objetos Autor

@Entity
@Table(name = "Autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String nombre;
    private double fechaNacimiento;
    private double fechaFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Libro> libros;

    // CONSTRUCTORES
    public Autor() {}
    public Autor(AutorDTO dto) {
        if (dto.nombre().equalsIgnoreCase("unknown") || dto.nombre().equalsIgnoreCase("desconocido") ||
                dto.nombre().equalsIgnoreCase("anonymous") || dto.nombre().equalsIgnoreCase("anónimo")) {
            this.nombre = "Desconocido";
            this.fechaNacimiento = -100000;
            this.fechaFallecimiento = -100000;
        } else {
            this.nombre = dto.nombre();
            this.fechaNacimiento = dto.fechaNacimiento();
            this.fechaFallecimiento = dto.fechaFallecimiento();
        }


    }
    public Autor(String nombre, double fechaNacimiento, double fechaFallecimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecimiento = fechaFallecimiento;
    }


    // Método para añadir objetos Libro y establecer su relación con Autor
    public void addLibro(Libro libro) {
        if (this.libros == null) {
            this.libros = new ArrayList<>();
        }
        this.libros.add(libro);
        libro.setAutor(this);
    }

    // Método para eliminar objetos Libro y romper su relación con Autor
    public void removeLibro(Libro libro) {
        this.libros.remove(libro);
        libro.setAutor(null);
    }


    // GETTERS
    public String getNombre() {
        return nombre;
    }
    public double getFechaNacimiento() {
        return fechaNacimiento;
    }
    public double getFechaFallecimiento() {
        return fechaFallecimiento;
    }
    public List<Libro> getLibros() {
        return libros;
    }

    // SETTERS
    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
