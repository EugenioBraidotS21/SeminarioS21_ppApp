package com.pandapickapp.view;

import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class BaseView<R extends Parent> {
    public static final double ANCHO_VENTANA = 380;
    public static final double ALTO_VENTANA = 520;

    protected R root;

    protected abstract void crearInterfaz();

    public R getRoot() {
        return root;
    }

    public Scene crearEscena() {
        return new Scene(root, ANCHO_VENTANA, ALTO_VENTANA);
    }

    public void limpiarVista() {
    }
}
