package com.eugepavia.challenge3.model;

import com.eugepavia.challenge3.dto.AutorDTO;

import java.util.List;

// Clase modelo para los objetos Autor

public class Autor {
    private String nombre;
    private double fechaNacimiento;
    private double fechaFallecimiento;
    private List<Libro> libros;

    public Autor(AutorDTO dto) {
        this.nombre = dto.nombre();
        this.fechaNacimiento = dto.fechaNacimiento();
        this.fechaFallecimiento = dto.fechaFallecimiento();
    }

    public Autor(String nombre, double fechaNacimiento, double fechaFallecimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecimiento = fechaFallecimiento;
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
}
