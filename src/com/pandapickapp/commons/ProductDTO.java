package com.pandapickapp.commons;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProductDTO {
    private final int id;
    private final StringProperty nombre;
    private final IntegerProperty quantity;
    private final FloatProperty precio;
    private final StringProperty rubro;

    public ProductDTO(int id, String nombre, int initialQuantity, float precio, String rubro) {
        this.id = id;
        this.nombre = new SimpleStringProperty(nombre);
        this.quantity = new SimpleIntegerProperty(initialQuantity);
        this.precio = new SimpleFloatProperty(precio);
        this.rubro = new SimpleStringProperty(rubro);
    }

    public int getId() { return id; }

    public String getNombre() { return nombre.get(); }
    public StringProperty nombreProperty() { return nombre; }

    public String getRubro() { return rubro.get(); }
    public StringProperty rubroProperty() { return rubro; }

    public float getPrecio() { return precio.get(); }
    public FloatProperty precioProperty() { return precio; }

    public int getQuantity() { return quantity.get(); }
    public void setQuantity(int quantity) { this.quantity.set(quantity); }
    public IntegerProperty quantityProperty() { return quantity; }

    public String getTextoFormateado() {
        return getNombre() + " — $" + String.format("%.0f", getPrecio());
    }
}