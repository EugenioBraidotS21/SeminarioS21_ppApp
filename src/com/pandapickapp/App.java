package com.pandapickapp;

import com.pandapickapp.controller.WelcomeController;
import com.pandapickapp.model.WelcomeModel;
import com.pandapickapp.view.WelcomeView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
    	// Instanciación MVC
        WelcomeModel model = new WelcomeModel();
        WelcomeView view = new WelcomeView();
        new WelcomeController(model, view, primaryStage);

        // Configuración de la ventana
        Scene scene = new Scene(view.getRoot(), 1000, 1000);
        primaryStage.setTitle("Bienvenido a Panda Pick APP");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
    }
    

    public static void main(String[] args) {  
        launch(args);
    }
}