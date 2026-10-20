package com.pandapickapp;

import com.pandapickapp.controller.WelcomeController;
import com.pandapickapp.model.WelcomeModel;
import com.pandapickapp.view.WelcomeView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;



/*
 * Class App:  Se decribe como App es una Aplicacion de 
 * 			   JavaFX basada en ventana de Windows. 
 * start: 	
 * 		Metodo heredado que envuelve la aplicacion en 
 * 		una estructura declarativa, dado que se definen las 
 * 		instanacias iniciales del Sistema.
 * Main: 
 * 		Tiene la misma mision que cualquier metodo estatico 
 * 		main(), ser el punto de entrada del programa. Que por un defecto 
 *  	de la libreria necesita de una instancia superior que la 
 *  	envuelva también.
 * */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
    	// Instanciación MVC para la Bievenida.
        WelcomeModel model = new WelcomeModel();
        WelcomeView view = new WelcomeView();
        new WelcomeController(model, view, primaryStage);

        // Configuración de la ventana inicial
        Scene scene = new Scene(view.getRoot(), 1000, 1000);
        primaryStage.setTitle("Bienvenido a Panda Pick APP");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show(); 
        
    }
    

    public static void main(String[] args) {  
        launch(args); // Ejecuta internamete la en la instancia APP y llama a Start().
    }
}