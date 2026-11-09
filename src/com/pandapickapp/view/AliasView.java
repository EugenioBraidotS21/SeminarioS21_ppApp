package com.pandapickapp.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;

public class AliasView {
    private VBox root;
    private TextField txtAlias;
    private Label lblError;
    private Button btnContinuar;
    private Button btnAtras;

    public AliasView() {
        crearInterfaz();
    }

    private void crearInterfaz() {
        // 1. Ícono de usuario con círculo exterior
        StackPane iconPane = crearIconoUsuario();

        // 2. Título principal
        Label lblTitulo = new Label("Ingrese su alias");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        // 3. Campo de texto con límite visible
        txtAlias = new TextField();
        txtAlias.setPromptText("Máximo 10 caracteres");
        txtAlias.setMaxWidth(240);
        txtAlias.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-border-color: #d1d5db; " +
            "-fx-border-radius: 6px; " +
            "-fx-background-radius: 6px; " +
            "-fx-padding: 8 12; " +
            "-fx-font-size: 14px; " +
            "-fx-prompt-text-fill: #9ca3af;"
        );

        // 4. Etiqueta de error
        lblError = new Label("");
        lblError.setStyle("-fx-text-fill: #b91c1c; -fx-font-size: 12px;");
        lblError.setVisible(false);
        lblError.setManaged(false); // No ocupa espacio cuando no se muestra
        
     // 5. Estilos compartidos para no duplicar código
        String estiloNormal = 
            "-fx-background-color: #ffffff; " +
            "-fx-border-color: #d1d5db; " +
            "-fx-border-radius: 8px; " +
            "-fx-background-radius: 8px; " +
            "-fx-text-fill: #111827; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: 600; " +
            "-fx-padding: 8 20; " +
            "-fx-cursor: hand;";

        String estiloHover = 
            "-fx-background-color: #f3f4f6; " +
            "-fx-border-color: #9ca3af; " +
            "-fx-border-radius: 8px; " +
            "-fx-background-radius: 8px; " +
            "-fx-text-fill: #111827; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: 600; " +
            "-fx-padding: 8 20; " +
            "-fx-cursor: hand;";

        // Botón Atras
        btnAtras = new Button("Atras");
        btnAtras.setMinWidth(120);
        btnAtras.setStyle(estiloNormal);
        btnAtras.setOnMouseEntered(e -> btnAtras.setStyle(estiloHover));
        btnAtras.setOnMouseExited(e -> btnAtras.setStyle(estiloNormal));

        // 7. Botón Continuar
        btnContinuar = new Button("Continuar");
        btnContinuar.setMinWidth(120);
        btnContinuar.setStyle(estiloNormal);
        btnContinuar.setOnMouseEntered(e -> btnContinuar.setStyle(estiloHover));
        btnContinuar.setOnMouseExited(e -> btnContinuar.setStyle(estiloNormal));

        // 8. HBox para colocarlos lado a lado
        HBox contenedorBotones = new HBox(12); // 12px de separación entre botones
        contenedorBotones.setAlignment(Pos.CENTER);
        contenedorBotones.getChildren().addAll(btnAtras, btnContinuar);
       
        // 9. Contenedor principal centrado
        root = new VBox(14);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #ffffff; -fx-padding: 40;");
        root.getChildren().addAll(iconPane, lblTitulo, txtAlias, lblError, contenedorBotones);
    }

    private StackPane crearIconoUsuario() {
        Circle circle = new Circle(22);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.web("#374151"));
        circle.setStrokeWidth(2.5);

        // Silueta cabeza y torso
        SVGPath userPath = new SVGPath();
        userPath.setContent(
            "M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"
        );
        userPath.setFill(Color.web("#374151"));
        userPath.setScaleX(1.1);
        userPath.setScaleY(1.1);

        StackPane pane = new StackPane(circle, userPath);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    public Parent getRoot() {
        return root;
    }

    public TextField getTxtAlias() {
        return txtAlias;
    }

    public Button getBtnContinuar() {
        return btnContinuar;
    }

    public void mostrarError(String mensaje) {
        lblError.setText(mensaje);
        lblError.setVisible(true);
        lblError.setManaged(true);
        txtAlias.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-border-color: #ef4444; " +
            "-fx-border-radius: 6px; " +
            "-fx-background-radius: 6px; " +
            "-fx-padding: 8 12; " +
            "-fx-font-size: 14px;"
        );
    }

    public void limpiarError() {
        lblError.setText("");
        lblError.setVisible(false);
        lblError.setManaged(false);
        txtAlias.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-border-color: #d1d5db; " +
            "-fx-border-radius: 6px; " +
            "-fx-background-radius: 6px; " +
            "-fx-padding: 8 12; " +
            "-fx-font-size: 14px;"
        );
    }

    public Button getBtnAtras() {
        return btnAtras;
    }

    /**
     * Limpia los componentes visuales para no retener datos en memoria.
     */
    public void limpiarVista() {
        if (txtAlias != null) {
            txtAlias.clear();
        }
        limpiarError();
    }
}