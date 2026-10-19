package com.pandapickapp.model;

public class ProductDTO {
	private int Id;
    public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	private String nombre;
    private double precio;
    private String rubro;

    public ProductDTO(String nombre, double precio, String rubro) {
        this.nombre = nombre;
        this.precio = precio;
        this.rubro = rubro;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public String getRubro() { return rubro; }

    public String getTextoFormateado() {
        return nombre + " — $" + String.format("%.0f", precio);
    }
}