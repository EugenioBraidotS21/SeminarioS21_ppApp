package com.pandapickapp.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WelcomeView {
    private VBox root;
    private Label lblTitulo;
    private Label lblSubtitulo;
    private Button btnIngresar;

    public WelcomeView() {
        crearInterfaz();
    }

    private void crearInterfaz() {
        lblTitulo = new Label();
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        lblSubtitulo = new Label();
        lblSubtitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        btnIngresar = new Button("Ingresar");
        btnIngresar.setStyle(
            "-fx-background-color: #3498db; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10 25; " +
            "-fx-background-radius: 6; " +
            "-fx-cursor: hand;"
        );

        // Hover básico
        btnIngresar.setOnMouseEntered(e -> btnIngresar.setStyle(
            "-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10 25; -fx-background-radius: 6; -fx-cursor: hand;"
        ));
        btnIngresar.setOnMouseExited(e -> btnIngresar.setStyle(
            "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10 25; -fx-background-radius: 6; -fx-cursor: hand;"
        ));

        root = new VBox(18);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 40;");
        root.getChildren().addAll(lblTitulo, lblSubtitulo, btnIngresar);
    }

    public Parent getRoot() {
        return root;
    }

    public Button getBtnIngresar() {
        return btnIngresar;
    }

    public void setTitulo(String texto) {
        lblTitulo.setText(texto);
    }

    public void setSubtitulo(String texto) {
        lblSubtitulo.setText(texto);
    }
}
