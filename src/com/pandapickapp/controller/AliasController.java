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
    private AliasModel model;
    private AliasView view;
    private Stage stage;
    private ChangeListener<String> textChangeListener;
    
    public AliasController(AliasModel model, AliasView view, Stage stage) {
        this.model = model;
        this.view = view;
        this.stage = stage;
        inicializar();
    }

    private void inicializar() {
    	
    	// Listener con referencia para poder desacoplarlo limpiamente
        textChangeListener = (obs, oldValue, newValue) -> {
            view.limpiarError();
            if (newValue != null && newValue.length() > AliasModel.MAX_CARACTERES) {
                view.getTxtAlias().setText(oldValue);
            }
        };
        view.getTxtAlias().textProperty().addListener(textChangeListener);
    	
        // Limitar la escritura a un máximo de 10 caracteres en tiempo real
        view.getTxtAlias().textProperty().addListener((obs, oldValue, newValue) -> {
            view.limpiarError();
            if (newValue != null && newValue.length() > AliasModel.MAX_CARACTERES) {
                view.getTxtAlias().setText(oldValue);
            }
        });

        // Evento botón Continuar
        view.getBtnContinuar().setOnAction(e -> onContinuar());

        // Permitir pulsar Enter en el campo de texto
        view.getTxtAlias().setOnAction(e -> onContinuar());
        
        view.getBtnCancelar().setOnAction(e -> onCancelar());
    }

    private void onContinuar() {
    	String texto = view.getTxtAlias().getText();
        String error = model.validar(texto);
        
        if (error != null) {
            view.mostrarError(error);
        } else {
            view.limpiarError();
            model.setAlias(texto);
            System.out.println("Alias válido: " + model.getAlias());

            RubrosModel rubrosModel = new RubrosModel();
            RubrosView rubrosView = new RubrosView(model);
            new RubrosController(rubrosModel, rubrosView, stage);

            Scene rubrosScene = new Scene(rubrosView.getRoot(), 1000, 1000);

            stage.setTitle("PandaPickApp - Selección de Rubros");
            stage.setScene(rubrosScene);
            }
    }
    
    private void onCancelar() {
        System.out.println("Cancelando ingreso de alias y regresando a Welcome...");

        // Guardar la referencia temporal del Stage antes de anular referencias
        Stage currentStage = this.stage;

        //Libera recursos y el estado de este controlador.
        dispose();

        //Recrea e inicializa el módulo Welcome
        WelcomeModel welcomeModel = new WelcomeModel();
        WelcomeView welcomeView = new WelcomeView();
        new WelcomeController(welcomeModel, welcomeView, currentStage);

        //Cambia la escena de regreso a Welcome
        Scene welcomeScene = new Scene(welcomeView.getRoot(), 380, 520);
        currentStage.setTitle("PandaPickApp - Bienvenido");
        currentStage.setScene(welcomeScene);
    }

    /**
     * Limpia el estado, desvincula listeners de JavaFX y anula referencias
     * para permitir que el Garbage Collector libere la memoria.
     */
    public void dispose() {
        if (this.view != null) {
            // Desvincular handlers de eventos
            this.view.getBtnContinuar().setOnAction(null);
            this.view.getBtnCancelar().setOnAction(null);
            this.view.getTxtAlias().setOnAction(null);

            // Desacopla el listener de texto
            if (textChangeListener != null) {
                this.view.getTxtAlias().textProperty().removeListener(textChangeListener);
                textChangeListener = null;
            }

            // Limpia los inputs visuales
            this.view.limpiarVista();
        }

        if (model != null) {
            model.setAlias("");
        }

        // Anula las referencias internas
        this.model = null;
        this.view = null;
        this.stage = null;
    }
}