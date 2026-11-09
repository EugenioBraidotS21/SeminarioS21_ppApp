package com.pandapickapp.controller;

import java.util.ArrayList;
import java.util.List;

import com.pandapickapp.commons.RubrosDTO;
import com.pandapickapp.model.AliasModel;
import com.pandapickapp.model.ProductModel;
import com.pandapickapp.model.RubrosModel;
import com.pandapickapp.view.AliasView;
import com.pandapickapp.view.ProductView;
import com.pandapickapp.view.RubrosView;

import javafx.scene.Scene;
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
    	
        if (model != null && view != null) {
            view.cargarRubros(model.getRubrosDisponibles());

            view.getCheckBoxes().forEach((rubro, cb) ->
                cb.selectedProperty().addListener((obs, anterior, seleccionado) ->
                    model.toggleRubro(rubro, seleccionado)));
        }
        
        if (view != null && view.getBtnVerProductos() != null) {
            view.getBtnVerProductos().setOnAction(e -> onVerProductos());
        }

        if (view != null && view.getBtnAtrasProducto() != null) {
            view.getBtnAtrasProducto().setOnAction(e -> onAtras());
        }
    }

    private void onVerProductos() {

        if (!model.tieneSeleccion()) {
            System.out.println("Debe seleccionar al menos un rubro para continuar.");
            return;
        }

        System.out.println("Rubros seleccionados: " + model.getRubrosSeleccionados());

        ProductModel prodModel = new ProductModel();
        ProductView prodView = new ProductView();
        new ProductController(prodModel, prodView, stage, model.getRubrosSeleccionados());

        Scene scene = new Scene(prodView.getRoot(), 380, 520);
        stage.setTitle("PandaPickApp - Elegí tus productos");
        stage.setScene(scene);
        
    }
    
    private void onAtras() {
        System.out.println("Cancelando ingreso de Rubros y regresando a Alias...");

        Stage currentStage = this.stage;

        dispose();

        AliasModel aliasModel = new AliasModel();
        AliasView aliasView = new AliasView();
        new AliasController(aliasModel, aliasView, currentStage);

        Scene aliasScene = new Scene(aliasView.getRoot(), 380, 520);
        currentStage.setTitle("PandaPickApp - Alias");
        currentStage.setScene(aliasScene);
    }

    public void dispose() {

    	if (view!=null) {
    		view.limpiarVista();
    	}
    	
        if (model != null) {
        	model.setRubrosDisponibles(null);
        }

        model = null;
        view = null;
        stage = null;
    }
    

}