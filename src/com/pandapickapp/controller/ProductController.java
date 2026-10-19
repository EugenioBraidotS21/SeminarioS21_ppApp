package com.pandapickapp.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pandapickapp.model.ProductDTO;
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
        // 1. Cargar productos base (los mostrados en la imagen)
        List<ProductDTO> catalogo = new ArrayList<ProductDTO>();
        catalogo.add(new ProductDTO("Bon o Bon", 500, "Golosinas"));
        catalogo.add(new ProductDTO("Alfajor Guaylen x40", 250, "Golosinas"));
        catalogo.add(new ProductDTO("Agua mineral 500ml", 350, "Bebidas"));
        catalogo.add(new ProductDTO("Gaseosa Cola 600ml", 600, "Bebidas"));
        catalogo.add(new ProductDTO("Papas Clásicas 85g", 700, "Snacks"));
        catalogo.add(new ProductDTO("Medialunas x3", 450, "Panificados"));

        this.model.setListaProductos(catalogo);

        // 2. Agrupar por rubro (filtrando según lo seleccionado en la pantalla previa si corresponde)
        Map<String, List<ProductDTO>> agrupados = new LinkedHashMap<>();
        for (ProductDTO p : catalogo) {
            if (rubrosFiltro == null || rubrosFiltro.isEmpty() || rubrosFiltro.contains(p.getRubro())) {
                agrupados.computeIfAbsent(p.getRubro(), k -> new ArrayList<>()).add(p);
            }
        }

        // 3. Renderizar la lista
        this.view.renderizarProductosPorRubro(agrupados, model.getMapaCantidades(), this::onProductoSeleccionado);

        // 4. Configurar eventos del modal emergente
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

        // 1. El modelo acumula la cantidad en el carrito
        model.confirmarSeleccionActual();

        // 2. Le pedimos al modelo la cantidad acumulada para este producto
        int cantidadTotal = model.getCantidad(seleccionado.getId());

        // 3. Le decimos a la vista que actualice SOLO esa fila usando su ID y cantidad
        view.actualizarCantidadProductoFila(seleccionado.getId(), cantidadTotal);

        // 4. Cerramos el modal
        view.ocultarModal();
    }
}