package com.pandapickapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RubrosModel extends BaseModel<String> {
    private Set<String> rubrosSeleccionados;

    public RubrosModel() {
        this.rubrosSeleccionados = new HashSet<>();
        cargarRubrosDesdeBD();
    }

    public List<String> getRubrosDisponibles() {
        return getItems();
    }

    public void setRubrosDisponibles(List<String> rubros) {
        setItems(rubros);
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

    @Override
    public void reset() {
        super.reset();
        this.rubrosSeleccionados = new HashSet<>();
    }

    private void cargarRubrosDesdeBD() {
        String sql = "SELECT nombre FROM rubros";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nombreRubro = rs.getString("nombre");
                items.add(nombreRubro);
            }
            System.out.println("Rubros cargados exitosamente desde la base de datos: " + items.size());

        } catch (Exception e) {
            System.err.println("Error al cargar los rubros desde la base de datos:");
            e.printStackTrace();
        }
    }
}
