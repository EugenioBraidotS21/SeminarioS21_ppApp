package com.pandapickapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pandapickapp.commons.ProductDTO;

public class ProductModel {
    private List<ProductDTO> ProductList;
    private ProductDTO productoSeleccionado;
    private int cantidadSeleccionada;
    private Map<ProductDTO, Integer> carrito;
    private static final String DB_URL = "jdbc:sqlite:Data/Pandita.db";
    
    public ProductModel() {
        this.ProductList = new ArrayList<>();
        this.carrito = new HashMap<>();
        this.cantidadSeleccionada = 1;
    }

    public List<ProductDTO> getListaProductos() {
        return ProductList;
    }

    public void setListaProductos(List<ProductDTO> listaProductos) {
        this.ProductList = listaProductos;
    }

    public ProductDTO getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(ProductDTO producto) {
        this.productoSeleccionado = producto;
        this.cantidadSeleccionada = 1; // Reinicia a 1 cada vez que se abre el modal
    }

    public int getCantidadSeleccionada() {
        return cantidadSeleccionada;
    }

    public void incrementarCantidad() {
        this.cantidadSeleccionada++;
    }

    public void decrementarCantidad() {
        if (this.cantidadSeleccionada <=1) {
        	throw new IllegalStateException("La cantidad no puede ser menor a 1.");
        }
        this.cantidadSeleccionada--;
    }
    public void confirmarSeleccionActual() {
        if (productoSeleccionado == null) {
        	throw new IllegalStateException("Debe seleccionar un producto para poder continuar.");
        }

        // Buscar si ya existe una entrada para este producto por ID
        ProductDTO existente = null;
        for (ProductDTO p : carrito.keySet()) {
            if (p.getId() == productoSeleccionado.getId()) {
                existente = p;
                break;
            }
        }

        if (existente != null) {
            carrito.put(existente, carrito.get(existente) + cantidadSeleccionada);
        } else {
            carrito.put(productoSeleccionado, cantidadSeleccionada);
        }
    }

    /**
     * Retorna un mapa con {idProducto -> cantidad} para poblar la vista.
     */
    public Map<Integer, Integer> getMapaCantidades() {
        Map<Integer, Integer> cantidades = new HashMap<>();
        for (Map.Entry<ProductDTO, Integer> entry : carrito.entrySet()) {
            cantidades.put(entry.getKey().getId(), entry.getValue());
        }
        return cantidades;
    }

    /**
     * Obtiene la cantidad seleccionada de un producto por su ID.
     */
    public int getCantidad(int idProducto) {
        for (Map.Entry<ProductDTO, Integer> entry : carrito.entrySet()) {
            if (entry.getKey().getId() == idProducto) {
                return entry.getValue();
            }
        }
        return 0;
    }
    
    public List<ProductDTO> cargarCatalogoDesdeBD() {
        List<ProductDTO> catalogo = new ArrayList<>();

        String sql = "SELECT p.id, p.nombre, p.cantidad,p.precio, r.nombre AS rubro "
                   + "FROM productos p "
                   + "JOIN rubros r ON r.id = p.rubro_id "
                   + "ORDER BY r.id, p.nombre";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                catalogo.add(new ProductDTO(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("cantidad"),
                    rs.getFloat("precio"),
                    rs.getString("rubro")
                ));
            }
            System.out.println("Productos cargados desde la base de datos: " + catalogo.size());

        } catch (SQLException e) {
            System.err.println("Error al cargar los productos desde la base de datos: " + e.getMessage());
        }

        return catalogo;
    }
    public Map<ProductDTO, Integer> getCarrito() {
        return carrito;
    }
}