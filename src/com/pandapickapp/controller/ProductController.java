package com.pandapickapp.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pandapickapp.commons.ProductDTO;
import com.pandapickapp.model.ProductModel;
import com.pandapickapp.view.ProductView;

import javafx.stage.Stage;

public class ProductController {
    private ProductModel model;
    private ProductView view;
    private Stage stage;
    private Set<String> rubrosFiltro;
  
    
    public ProductController(ProductModel model, ProductView view, Stage stage, Set<String> rubrosFiltro) {
        this.model = model;
        this.view = view;
        this.stage = stage;
        this.rubrosFiltro = rubrosFiltro;
        inicializar();
    }

      
    private void inicializar() {
        // Armamos un array como catalogo de productos hasta tener la Base operativa
        List<ProductDTO> catalogo = model.cargarCatalogoDesdeBD();
        this.model.setListaProductos(catalogo);

        //Agrupamos por rubro (filtrando según lo seleccionado en la pantalla previa si corresponde)
        Map<String, List<ProductDTO>> agrupados = new LinkedHashMap<>();
        for (ProductDTO p : catalogo) {
            if (rubrosFiltro == null || rubrosFiltro.isEmpty() || rubrosFiltro.contains(p.getRubro())) {
                agrupados.computeIfAbsent(p.getRubro(), k -> new ArrayList<>()).add(p);
            }
        }

        //El controller envia a renderiza la lista
        this.view.renderizarProductosPorRubro(agrupados, model.getMapaCantidades(), this::onProductoSeleccionado);

        // Configura los eventos del modal emergente
        view.getBtnMenos().setOnAction(e -> {
            model.decrementarCantidad();
            view.actualizarCantidadModal(model.getCantidadSeleccionada());
        });

        view.getBtnMas().setOnAction(e -> {
            model.incrementarCantidad();
            view.actualizarCantidadModal(model.getCantidadSeleccionada());
        });

        view.getBtnConfirmar().setOnAction(e -> onConfirmarProducto());
    }

    private void onProductoSeleccionado(ProductDTO p) {
        model.setProductoSeleccionado(p);
        view.mostrarModal(p.getNombre(), model.getCantidadSeleccionada());
    }

    private void onConfirmarProducto() {
        ProductDTO seleccionado = model.getProductoSeleccionado();
        if (seleccionado == null) return;

        //El modelo acumula la cantidad en el carrito
        model.confirmarSeleccionActual();

        //Le pedimos al modelo la cantidad acumulada para este producto
        int cantidadTotal = model.getCantidad(seleccionado.getId());

        //Le decimos a la vista que actualice SOLO esa fila usando su ID y cantidad
        view.actualizarCantidadProductoFila(seleccionado.getId(), cantidadTotal);

        // Cerramos el modal
        view.ocultarModal();
    }
}