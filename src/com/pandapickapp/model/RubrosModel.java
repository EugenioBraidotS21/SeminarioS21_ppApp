package com.pandapickapp.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RubrosModel {
    private List<String> rubrosDisponibles;
    private Set<String> rubrosSeleccionados;

    public RubrosModel() {
        this.rubrosDisponibles = new ArrayList<>();
        this.rubrosSeleccionados = new HashSet<>();
    }

    public List<String> getRubrosDisponibles() {
        return rubrosDisponibles;
    }

    public void setRubrosDisponibles(List<String> rubros) {
        this.rubrosDisponibles = rubros;
    }

    public Set<String> getRubrosSeleccionados() {
        return rubrosSeleccionados;
    }

    public void toggleRubro(String rubro, boolean seleccionado) {
        if (seleccionado) {
            rubrosSeleccionados.add(rubro);
        } else {
            rubrosSeleccionados.remove(rubro);
        }
    }

    public boolean tieneSeleccion() {
        return !rubrosSeleccionados.isEmpty();
    }
}
