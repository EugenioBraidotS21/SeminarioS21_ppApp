package com.pandapickapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RubrosModel {
    private List<String> rubrosDisponibles;
    private Set<String> rubrosSeleccionados;
    // Ruta relativa de la base de datos
    private static final String DB_URL = "jdbc:sqlite:Data/Pandita.db";

    public RubrosModel() {
        this.rubrosDisponibles = new ArrayList<>();
        this.rubrosSeleccionados = new HashSet<>();
        cargarRubrosDesdeBD();
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
    
    private void cargarRubrosDesdeBD() {
        String sql = "SELECT nombre FROM rubros";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nombreRubro = rs.getString("nombre");
                rubrosDisponibles.add(nombreRubro);
            }
            System.out.println("Rubros cargados exitosamente desde la base de datos: " + rubrosDisponibles.size());

        } catch (Exception e) {
            System.err.println("Error al cargar los rubros desde la base de datos:");
            e.printStackTrace();
        }
    }
}