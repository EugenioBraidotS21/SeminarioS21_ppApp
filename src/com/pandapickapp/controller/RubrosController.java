package com.pandapickapp.controller;

import com.pandapickapp.model.AliasModel;

import com.pandapickapp.model.ProductModel;
import com.pandapickapp.model.RubrosModel;
import com.pandapickapp.view.AliasView;
import com.pandapickapp.view.ProductView;
import com.pandapickapp.view.RubrosView;

import javafx.scene.Scene;
import javafx.stage.Stage;


/*
 * RubrosController<V,M>:
 * 						Podemos decir que con los parámetros genericos recibidos el controller 
 * 						de rubros conoce el modelo y la vista.
 * */

public class RubrosController extends BaseController<RubrosModel, RubrosView> {

    public RubrosController(RubrosModel model, RubrosView view, Stage stage) {
        super(model, view, stage);
        inicializar();
    }

    @Override
    protected void inicializar() {
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

        Scene scene = prodView.crearEscena();
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

        Scene aliasScene = aliasView.crearEscena();
        currentStage.setTitle("PandaPickApp - Alias");
        currentStage.setScene(aliasScene);
    }
}
