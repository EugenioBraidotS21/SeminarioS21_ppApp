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
 * BaseController<M,V> : 
 * 					AliasController extiende de la clase BaseController
 * 					aquellos metodos comunes a todos los controller. 
 * 					Podemos decir coloquialmente, que el controller conoce
 * 					al modelo y a la vista.  
 * 
 * */

public class AliasController extends BaseController<AliasModel, AliasView> {
    private RubrosModel rubrosModel;
    private ChangeListener<String> textChangeListener;

    public AliasController(AliasModel model, AliasView view, Stage stage) {
        super(model, view, stage);
        inicializar();
    }

    @Override
    protected void inicializar() {
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
        String error = model.validar(texto);

        if (error != null) {
            view.mostrarError(error);
        } else {
            view.limpiarError();
            model.setAlias(texto);
            System.out.println("Alias válido: " + model.getAlias());
            this.rubrosModel = new RubrosModel();
            // Inicializamos el modelo de rubros y se lo pasamos a su respectiva vista/controlador
            RubrosView rubrosView = new RubrosView(this.model, this.rubrosModel);
            Scene rubrosScene = rubrosView.crearEscena();
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

        Scene welcomeScene = welcomeView.crearEscena();
        currentStage.setTitle("PandaPickApp - Bienvenido");
        currentStage.setScene(welcomeScene);
    }

    @Override
    public void dispose() {
        if (view != null) {
            view.getBtnContinuar().setOnAction(null);
            view.getBtnAtras().setOnAction(null);
            view.getTxtAlias().setOnAction(null);

            if (textChangeListener != null) {
                view.getTxtAlias().textProperty().removeListener(textChangeListener);
                textChangeListener = null;
            }
        }

        super.dispose();
    }
}
