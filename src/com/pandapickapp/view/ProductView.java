package com.pandapickapp.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.pandapickapp.model.ProductDTO;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ProductView {
    private StackPane root;
    private VBox contenedorLista;
    private Label lblTitulo;

    // Componentes del Modal Flotante
    private StackPane overlayOscuro;
    private VBox cardModal;
    private Label lblModalProducto;
    private Label lblModalCantidad;
    private Button btnMenos;
    private Button btnMas;
    private Button btnCancelar;
    private Button btnEnviar;
    private Button btnConfirmar;
    

    // Mapa para actualizar rápidamente las etiquetas de cantidad en cada tarjeta
    private Map<Integer, Label> etiquetasCantidad;

    public ProductView() {
        this.etiquetasCantidad = new HashMap<>();
        crearInterfaz();
    }

    private void crearInterfaz() {
        root = new StackPane();
        root.setStyle("-fx-background-color: #ffffff;");

        // 1. Vista Principal con Scroll
        VBox mainContent = new VBox(18);
        mainContent.setPadding(new Insets(25, 20, 20, 20));
        mainContent.setAlignment(Pos.TOP_CENTER);

        lblTitulo = new Label("Seleccione un producto");
        lblTitulo.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        contenedorLista = new VBox(16);
        contenedorLista.setFillWidth(true);

        ScrollPane scroll = new ScrollPane(contenedorLista);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        
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

        // 6. Botón Cancelar
        btnCancelar = new Button("Cancelar");
        btnCancelar.setMinWidth(120);
        btnCancelar.setStyle(estiloNormal);
        btnCancelar.setOnMouseEntered(e -> btnCancelar.setStyle(estiloHover));
        btnCancelar.setOnMouseExited(e -> btnCancelar.setStyle(estiloNormal));

        // 7. Botón Enviar
        btnEnviar = new Button("Enviar");
        btnEnviar.setMinWidth(120);
        btnEnviar.setStyle(estiloNormal);
        btnEnviar.setOnMouseEntered(e -> btnEnviar.setStyle(estiloHover));
        btnEnviar.setOnMouseExited(e -> btnEnviar.setStyle(estiloNormal));

        // 8. HBox para colocarlos lado a lado
        HBox contenedorBotones = new HBox(20); // 12px de separación entre botones
        contenedorBotones.setAlignment(Pos.BOTTOM_CENTER);
        contenedorBotones.getChildren().addAll(btnCancelar, btnEnviar);
        
        
        mainContent.getChildren().addAll(lblTitulo, scroll,contenedorBotones );

        // 2. Overlay y Modal Emergente
        crearModal();

        root.getChildren().addAll(mainContent, overlayOscuro);
    }

    private void crearModal() {
        overlayOscuro = new StackPane();
        overlayOscuro.setStyle("-fx-background-color: rgba(0, 0, 0, 0.45);");
        overlayOscuro.setVisible(false);
        overlayOscuro.setManaged(false);

        // Tarjeta blanca modal
        cardModal = new VBox(16);
        cardModal.setAlignment(Pos.CENTER);
        cardModal.setMaxWidth(250);
        cardModal.setPadding(new Insets(20, 18, 18, 18));
        cardModal.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-background-radius: 14px; " +
            "-fx-border-radius: 14px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.18), 10, 0, 0, 4);"
        );

        lblModalProducto = new Label("Bon o Bon");
        lblModalProducto.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        // Selector con botones redondos: [ - ]  1  [ + ]
        btnMenos = crearBotonPildora("-");
        btnMas = crearBotonPildora("+");

        lblModalCantidad = new Label("1");
        lblModalCantidad.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #111827; -fx-min-width: 28px;");
        lblModalCantidad.setAlignment(Pos.CENTER);

        HBox controlCantidad = new HBox(12, btnMenos, lblModalCantidad, btnMas);
        controlCantidad.setAlignment(Pos.CENTER);

        btnConfirmar = new Button("Confirmar Cantidad");
        btnConfirmar.setMaxWidth(Double.MAX_VALUE);
        btnConfirmar.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-border-color: #d1d5db; " +
            "-fx-border-radius: 8px; " +
            "-fx-background-radius: 8px; " +
            "-fx-text-fill: #111827; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: 600; " +
            "-fx-padding: 7 20; " +
            "-fx-cursor: hand;"
        );

        btnConfirmar.setOnMouseEntered(e -> btnConfirmar.setStyle(
            "-fx-background-color: #f3f4f6; -fx-border-color: #9ca3af; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: #111827; -fx-font-size: 14px; -fx-font-weight: 600; -fx-padding: 7 20; -fx-cursor: hand;"
        ));
        btnConfirmar.setOnMouseExited(e -> btnConfirmar.setStyle(
            "-fx-background-color: #ffffff; -fx-border-color: #d1d5db; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: #111827; -fx-font-size: 14px; -fx-font-weight: 600; -fx-padding: 7 20; -fx-cursor: hand;"
        ));

        cardModal.getChildren().addAll(lblModalProducto, controlCantidad, btnConfirmar);
        overlayOscuro.getChildren().add(cardModal);

        // Cerrar si se hace clic fuera del modal
        overlayOscuro.setOnMouseClicked(e -> {
            if (e.getTarget() == overlayOscuro) {
                ocultarModal();
            }
        });
    }

    private Button crearBotonPildora(String texto) {
        Button btn = new Button(texto);
        btn.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-border-color: #d1d5db; " +
            "-fx-border-radius: 12px; " +
            "-fx-background-radius: 12px; " +
            "-fx-font-size: 13px; " +
            "-fx-font-weight: bold; " +
            "-fx-text-fill: #374151; " +
            "-fx-min-width: 32px; " +
            "-fx-min-height: 24px; " +
            "-fx-cursor: hand;"
        );
        return btn;
    }

    /**
     * Dibuja los productos agrupados con su respectivo contador de cantidad.
     */
    public void renderizarProductosPorRubro(Map<String, List<ProductDTO>> productosAgrupados, 
                                           Map<Integer, Integer> cantidadesIniciales, 
                                           Consumer<ProductDTO> onProductoClick) {
        contenedorLista.getChildren().clear();
        etiquetasCantidad.clear();

        for (Map.Entry<String, List<ProductDTO>> entry : productosAgrupados.entrySet()) {
            String rubro = entry.getKey();
            List<ProductDTO> lista = entry.getValue();

            // Etiqueta del rubro
            Label lblRubro = new Label(rubro);
            lblRubro.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #4b5563; -fx-padding: 4 0 0 2;");
            contenedorLista.getChildren().add(lblRubro);

            // Generación de la tarjeta con nombre, precio y cantidad
            for (ProductDTO p : lista) {
                HBox cardFila = new HBox(8);
                cardFila.setAlignment(Pos.CENTER_LEFT);
                cardFila.setPadding(new Insets(10, 14, 10, 14));
                cardFila.setMaxWidth(Double.MAX_VALUE);
                cardFila.setCursor(javafx.scene.Cursor.HAND);

                String estiloBase = 
                    "-fx-background-color: #f3f4f6; " +
                    "-fx-border-color: #d1d5db; " +
                    "-fx-border-radius: 8px; " +
                    "-fx-background-radius: 8px;";
                String estiloHover = 
                    "-fx-background-color: #e5e7eb; " +
                    "-fx-border-color: #9ca3af; " +
                    "-fx-border-radius: 8px; " +
                    "-fx-background-radius: 8px;";

                cardFila.setStyle(estiloBase);
                cardFila.setOnMouseEntered(e -> cardFila.setStyle(estiloHover));
                cardFila.setOnMouseExited(e -> cardFila.setStyle(estiloBase));

                // 1. Nombre y precio
                Label lblTexto = new Label(p.getTextoFormateado());
                lblTexto.setStyle("-fx-font-size: 13px; -fx-font-weight: 500; -fx-text-fill: #111827;");

                // 2. Espaciador flexible para empujar la cantidad a la derecha
                Region espaciador = new Region();
                HBox.setHgrow(espaciador, Priority.ALWAYS);

                // 3. Etiqueta / Badge de cantidad (inicia en 0)
                int cant = (cantidadesIniciales != null && cantidadesIniciales.containsKey(p.getId())) 
                            ? cantidadesIniciales.get(p.getId()) 
                            : 0;

                Label lblCant = new Label(formatearTextoCantidad(cant));
                aplicarEstiloCantidad(lblCant, cant);

                etiquetasCantidad.put(p.getId(), lblCant);

                cardFila.getChildren().addAll(lblTexto, espaciador, lblCant);
                cardFila.setOnMouseClicked(e -> onProductoClick.accept(p));

                contenedorLista.getChildren().add(cardFila);
            }
        }
    }

    /**
     * Actualiza visualmente la cantidad de un producto específico.
     */
    public void actualizarCantidadProductoFila(int productoId, int cantidad) {
        Label lbl = etiquetasCantidad.get(productoId);
        if (lbl != null) {
            lbl.setText(formatearTextoCantidad(cantidad));
            aplicarEstiloCantidad(lbl, cantidad);
        }
    }

    private String formatearTextoCantidad(int cantidad) {
        return "x " + cantidad;
    }

    private void aplicarEstiloCantidad(Label lbl, int cantidad) {
        if (cantidad > 0) {
            // Destacado si hay unidades seleccionadas
            lbl.setStyle(
                "-fx-background-color: #111827; " +
                "-fx-text-fill: #ffffff; " +
                "-fx-font-size: 11px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 2 8 2 8; " +
                "-fx-background-radius: 10px;"
            );
        } else {
            // Estilo neutro y sutil para cantidad 0
            lbl.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-text-fill: #9ca3af; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: normal; " +
                "-fx-padding: 2 4 2 4;"
            );
        }
    }

    public void mostrarModal(String nombreProducto, int cantidad) {
        lblModalProducto.setText(nombreProducto);
        actualizarCantidadModal(cantidad);
        overlayOscuro.setVisible(true);
        overlayOscuro.setManaged(true);
    }

    public void ocultarModal() {
        overlayOscuro.setVisible(false);
        overlayOscuro.setManaged(false);
    }

    public void actualizarCantidadModal(int cantidad) {
        lblModalCantidad.setText(String.valueOf(cantidad));
    }

    public Parent getRoot() { return root; }
    public Button getBtnMenos() { return btnMenos; }
    public Button getBtnMas() { return btnMas; }
    public Button getBtnConfirmar() { return btnConfirmar; }
}