package com.pandapickapp.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pandapickapp.model.AliasModel;
import com.pandapickapp.model.RubrosModel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class RubrosView extends BaseView<VBox> {
    private VBox contenedorRubros;
    private Button btnVerProductos;
    private Button btnAtrasProductos;
    private Map<String, CheckBox> checkBoxes;
    private AliasModel aliasModel;
    private RubrosModel rubrosModel;

    public RubrosView(AliasModel aliasModel, RubrosModel rubrosModel) {
        this.checkBoxes = new HashMap<>();
        this.rubrosModel= rubrosModel;
        this.aliasModel= aliasModel;
        crearInterfaz();
    }


    @Override
    protected void crearInterfaz() {
        
    	 Label lblHeader = new Label("Bienvenido/a "+ aliasModel.getAlias().toUpperCase());
    	 lblHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #111827;");
    	 lblHeader.setAlignment(Pos.BOTTOM_RIGHT);
    	 lblHeader.setMaxWidth(Double.MAX_VALUE);
    	
    	// Título principal centrado
        Label lblTitulo = new Label("Seleccione uno o más rubros");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        lblTitulo.setAlignment(Pos.CENTER);
        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        

        // Contenedor vertical de los ítems de rubro
        contenedorRubros = new VBox(12);
        contenedorRubros.setAlignment(Pos.TOP_CENTER);

        // Espaciador flexible para empujar el botón al fondo
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Botón inferior 'Ver productos'
        btnVerProductos = new Button("Ver productos");
        btnVerProductos.setMaxWidth(Double.MAX_VALUE);
        btnVerProductos.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-border-color: #d1d5db; " +
            "-fx-border-radius: 8px; " +
            "-fx-background-radius: 8px; " +
            "-fx-text-fill: #111827; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: 600; " +
            "-fx-padding: 9 20; " +
            "-fx-cursor: hand;"
        );
        
        btnAtrasProductos = new Button("Atras");
        btnAtrasProductos.setMaxWidth(Double.MAX_VALUE);
        btnAtrasProductos.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-border-color: #d1d5db; " +
            "-fx-border-radius: 8px; " +
            "-fx-background-radius: 8px; " +
            "-fx-text-fill: #111827; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: 600; " +
            "-fx-padding: 9 20; " +
            "-fx-cursor: hand;"
        );

        btnVerProductos.setOnMouseEntered(e -> btnVerProductos.setStyle(
            "-fx-background-color: #f3f4f6; -fx-border-color: #9ca3af; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: #111827; -fx-font-size: 14px; -fx-font-weight: 600; -fx-padding: 9 20; -fx-cursor: hand;"
        ));
        btnVerProductos.setOnMouseExited(e -> btnVerProductos.setStyle(
            "-fx-background-color: #ffffff; -fx-border-color: #d1d5db; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: #111827; -fx-font-size: 14px; -fx-font-weight: 600; -fx-padding: 9 20; -fx-cursor: hand;"
        ));
        
        btnAtrasProductos.setOnMouseEntered(e -> btnAtrasProductos.setStyle(
                "-fx-background-color: #f3f4f6; -fx-border-color: #9ca3af; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: #111827; -fx-font-size: 14px; -fx-font-weight: 600; -fx-padding: 9 20; -fx-cursor: hand;"
            ));
        btnAtrasProductos.setOnMouseExited(e -> btnAtrasProductos.setStyle(
                "-fx-background-color: #ffffff; -fx-border-color: #d1d5db; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: #111827; -fx-font-size: 14px; -fx-font-weight: 600; -fx-padding: 9 20; -fx-cursor: hand;"
            ));
        
        // 8. HBox para colocarlos lado a lado
        HBox contenedorBotones = new HBox(12); // 12px de separación entre botones
        contenedorBotones.setAlignment(Pos.CENTER);
        contenedorBotones.getChildren().addAll(btnAtrasProductos, btnVerProductos);
        
        // Layout general
        root = new VBox(20);
        root.setPadding(new Insets(30, 24, 25, 24));
        root.setStyle("-fx-background-color: #ffffff;");
        this.cargarRubros(rubrosModel.getRubrosDisponibles());
        root.getChildren().addAll(lblHeader,lblTitulo, contenedorRubros, spacer, contenedorBotones);
    }

    /**
     * Genera visualmente las cajas de cada rubro con bordes redondeados
     */
    public void cargarRubros(List<String> rubros) {
        contenedorRubros.getChildren().clear();
        checkBoxes.clear();

        if (rubros == null || rubros.isEmpty()) {
            Label vacio = new Label("No hay rubros disponibles");
            vacio.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 14px;");
            contenedorRubros.getChildren().add(vacio);
            return;
        }

        for (String rubro : rubros) {
            CheckBox cb = new CheckBox(rubro);
            cb.setStyle("-fx-font-size: 14px; -fx-text-fill: #111827; -fx-font-weight: 500;");
            cb.setCursor(javafx.scene.Cursor.HAND);

            // Contenedor redondeado como tarjeta para cada opción
            VBox boxItem = new VBox(cb);
            boxItem.setPadding(new Insets(10, 14, 10, 14));
            boxItem.setMaxWidth(Double.MAX_VALUE);
            boxItem.setStyle(
                "-fx-background-color: #ffffff; " +
                "-fx-border-color: #d1d5db; " +
                "-fx-border-radius: 8px; " +
                "-fx-background-radius: 8px;"
            );

            // Hacer clic en toda la tarjeta activa el CheckBox
            boxItem.setOnMouseClicked(e -> cb.setSelected(!cb.isSelected()));
            cb.setOnMouseClicked(e -> e.consume());

            checkBoxes.put(rubro, cb);
            contenedorRubros.getChildren().add(boxItem);
        }
    }

    public Map<String, CheckBox> getCheckBoxes() {
        return checkBoxes;
    }


	public Button getBtnVerProductos() {
		return btnVerProductos;
	}


	public Button getBtnAtrasProducto() {
		return  btnAtrasProductos;
	}
}
