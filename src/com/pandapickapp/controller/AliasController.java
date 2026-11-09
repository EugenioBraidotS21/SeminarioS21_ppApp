package com.pandapickapp.controller;

import com.pandapickapp.model.AliasModel;
import com.pandapickapp.model.RubrosModel;
import com.pandapickapp.model.WelcomeModel;
import com.pandapickapp.view.AliasView;
import com.pandapickapp.view.RubrosView;
import com.pandapickapp.view.WelcomeView;

import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * AliasController:
 * 					En esta clase manejamos lo referente al Alias, 
 * 					controler se lo pasa al modelo para que lo conserve y 
 * 					se lo da a conocer a la Vista.
 * */

public class AliasController {
    private AliasModel aliasmodel;
    private RubrosModel rubrosModel;
    private AliasView view;
    private Stage stage;
    private ChangeListener<String> textChangeListener;
    
    public AliasController(AliasModel model, AliasView view, Stage stage) {
        this.aliasmodel = model;
        this.view = view;
        this.stage = stage;
        inicializar();
    }

    private void inicializar() {
        // Listener único para limitar la escritura a un máximo de caracteres y limpiar errores
        textChangeListener = (obs, oldValue, newValue) -> {
            view.limpiarError();
            if (newValue != null && newValue.length() > AliasModel.MAX_CARACTERES) {
                view.getTxtAlias().setText(oldValue);
            }
        };
        view.getTxtAlias().textProperty().addListener(textChangeListener);

        // Evento botón Continuar
        view.getBtnContinuar().setOnAction(e -> onContinuar());

        // Permitir pulsar Enter en el campo de texto
        view.getTxtAlias().setOnAction(e -> onContinuar());
        
        view.getBtnAtras().setOnAction(e -> onAtras());
    }

    private void onContinuar() {
        String texto = view.getTxtAlias().getText();
        String error = aliasmodel.validar(texto);
        
        if (error != null) {
            view.mostrarError(error);
        } else {
            view.limpiarError();
            aliasmodel.setAlias(texto);
            System.out.println("Alias válido: " + aliasmodel.getAlias());
            this.rubrosModel= new RubrosModel();
            // Inicializamos el modelo de rubros y se lo pasamos a su respectiva vista/controlador
            RubrosView rubrosView = new RubrosView(this.aliasmodel, this.rubrosModel);
            Scene rubrosScene = new Scene(rubrosView.getRoot(), 380, 520);
            stage.setTitle("PandaPickApp - Selección de Rubros");
            new RubrosController(this.rubrosModel, rubrosView, stage);
            stage.setScene(rubrosScene);
        }
    }
    
    private void onAtras() {
        System.out.println("Cancelando ingreso de alias y regresando a Welcome...");

        Stage currentStage = this.stage;

        dispose();

        WelcomeModel welcomeModel = new WelcomeModel();
        WelcomeView welcomeView = new WelcomeView();
        new WelcomeController(welcomeModel, welcomeView, currentStage);

        Scene welcomeScene = new Scene(welcomeView.getRoot(), 380, 520);
        currentStage.setTitle("PandaPickApp - Bienvenido");
        currentStage.setScene(welcomeScene);
    }

    public void dispose() {
        if (this.view != null) {
            this.view.getBtnContinuar().setOnAction(null);
            this.view.getBtnAtras().setOnAction(null);
            this.view.getTxtAlias().setOnAction(null);

            if (textChangeListener != null) {
                this.view.getTxtAlias().textProperty().removeListener(textChangeListener);
                textChangeListener = null;
            }

            this.view.limpiarVista();
        }

        if (aliasmodel != null) {
            aliasmodel.setAlias("");
        }

        this.aliasmodel = null;
        this.view = null;
        this.stage = null;
    }
}