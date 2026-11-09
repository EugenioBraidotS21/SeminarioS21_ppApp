package com.pandapickapp.controller;

import com.pandapickapp.model.AliasModel;
import com.pandapickapp.model.WelcomeModel;
import com.pandapickapp.view.AliasView;
import com.pandapickapp.view.WelcomeView;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class WelcomeController extends BaseController<WelcomeModel, WelcomeView> {

    public WelcomeController(WelcomeModel model, WelcomeView view, Stage stage) {
        super(model, view, stage);
        inicializar();
    }

    @Override
    protected void inicializar() {
        // Enlazar datos iniciales
        view.setTitulo(model.getTitulo());
        view.setSubtitulo(model.getSubtitulo());

        // Manejar eventos
        view.getBtnIngresar().setOnAction(e -> onIngresarClicked());
    }

    private void onIngresarClicked() {
        System.out.println("Navegando a la pantalla de Alias...");

        // Obtener la ventana actual desde cualquier control de la vista
        Stage currentStage = (Stage) view.getBtnIngresar().getScene().getWindow();

        // Instanciar el nuevo módulo
        AliasModel aliasModel = new AliasModel();
        AliasView aliasView = new AliasView();
        new AliasController(aliasModel, aliasView, currentStage);

        Scene aliasScene = aliasView.crearEscena();
        currentStage.setTitle("PandaPickApp - Ingrese su alias");
        currentStage.setScene(aliasScene);
        currentStage.centerOnScreen();
    }
}
