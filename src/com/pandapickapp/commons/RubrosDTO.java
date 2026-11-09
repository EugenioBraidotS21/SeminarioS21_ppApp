package com.pandapickapp.commons;

import java.io.Serializable;
import java.util.Objects;

/**
 * RubrosDTO: Objeto de transferencia de datos para la entidad Rubro.
 * Versión: 1.0.0 (SemVer)
 */
public class RubrosDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;

    public RubrosDTO() {
    }

    public RubrosDTO(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RubrosDTO rubrosDTO = (RubrosDTO) o;
        return id == rubrosDTO.id && Objects.equals(nombre, rubrosDTO.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public String toString() {
        return nombre; // Útil si se vincula directamente a componentes visuales o listas
    }
}