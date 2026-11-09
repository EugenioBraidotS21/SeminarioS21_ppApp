package com.pandapickapp.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pandapickapp.commons.ProductDTO;
import com.pandapickapp.model.ProductModel;
import com.pandapickapp.view.ProductView;

import javafx.stage.Stage;

/*
 * ProductControler():
 * 				Contructor que recibe Model, View, Stage y una lista de rubros seleccionados.
 * ProductController<M,V> : 
 * 					ProductController extiende de la clase BaseController
 * 					aquellos metodos comunes. 
 * 					Podemos decir coloquialmente, que el controller conoce
 * 					al modelo y a la vista.  
 * */
public class ProductController extends BaseController<ProductModel, ProductView> {
    private Set<String> rubrosFiltro;

    public ProductController(ProductModel model, ProductView view, Stage stage, Set<String> rubrosFiltro) {
        super(model, view, stage);
        this.rubrosFiltro = rubrosFiltro;
        inicializar();
    }

    @Override
    protected void inicializar() {
        List<ProductDTO> catalogo = model.cargarCatalogoDesdeBD();
        model.setListaProductos(catalogo);

        //Agrupamos por rubro (filtrando según lo seleccionado en la pantalla previa si corresponde)
        Map<String, List<ProductDTO>> agrupados = new LinkedHashMap<>();
        for (ProductDTO p : catalogo) {
            if (rubrosFiltro == null || rubrosFiltro.isEmpty() || rubrosFiltro.contains(p.getRubro())) {
                agrupados.computeIfAbsent(p.getRubro(), k -> new ArrayList<>()).add(p);
            }
        }

        //El controller envia a renderiza la lista
        view.renderizarProductosPorRubro(agrupados, model.getMapaCantidades(), this::onProductoSeleccionado);

        // Configura los eventos del modal emergente
        view.getBtnMenos().setOnAction(e -> {
            if (model.getCantidadSeleccionada() > 1) {
                model.decrementarCantidad();
                view.actualizarCantidadModal(model.getCantidadSeleccionada());
            }
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
