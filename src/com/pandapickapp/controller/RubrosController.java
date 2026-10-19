package com.pandapickapp.controller;

import java.util.Arrays;
import java.util.List;

import com.pandapickapp.model.ProductModel;
import com.pandapickapp.model.RubrosModel;
import com.pandapickapp.view.ProductView;
import com.pandapickapp.view.RubrosView;

import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class RubrosController {
    private RubrosModel model;
    private RubrosView view;
    private Stage stage;

    public RubrosController(RubrosModel model, RubrosView view, Stage stage) {
        this.model = model;
        this.view = view;
        this.stage = stage;
        inicializar();
    }

    private void inicializar() {
        // 1. Armar la lista de rubros de la pantalla
        List<String> rubros = Arrays.asList(
            "Golosinas",
            "Bebidas",
            "Snacks",
            "Panificados"
        );

        // 2. Pasarlos al modelo y a la vista
        model.setRubrosDisponibles(rubros);
        view.cargarRubros(rubros);

        // 3. Vincular cambios de estado de cada CheckBox con el modelo
        for (String rubro : rubros) {
            CheckBox cb = view.getCheckBoxes().get(rubro);
            if (cb != null) {
                cb.selectedProperty().addListener((obs, oldVal, newVal) -> {
                    model.toggleRubro(rubro, newVal);
                });
            }
        }

        // 4. Seleccionar 'Golosinas' por defecto tal como aparece en la imagen
        CheckBox cbGolosinas = view.getCheckBoxes().get("Golosinas");
        if (cbGolosinas != null) {
            cbGolosinas.setSelected(true);
        }

        // 5. Manejar clic en "Ver productos"
        view.getBtnVerProductos().setOnAction(e -> onVerProductosClicked());
    }

    private void onVerProductosClicked() {
        if (!model.tieneSeleccion()) {
            System.out.println("Debe seleccionar al menos un rubro para continuar.");
            return;
        }

        System.out.println("Rubros seleccionados: " + model.getRubrosSeleccionados());

        // 1. Instanciar el MVC de Productos pasando los rubros seleccionados
        ProductModel prodModel = new ProductModel();
        ProductView prodView = new ProductView();
        new ProductController(prodModel, prodView, stage, model.getRubrosSeleccionados());

        // 2. Crear y fijar la nueva escena en el Stage actual
        Scene scene = new Scene(prodView.getRoot(), 380, 520);
        stage.setTitle("PandaPickApp - Elegí tus productos");
        stage.setScene(scene);
    }
}