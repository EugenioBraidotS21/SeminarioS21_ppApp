package com.pandapickapp.model;

public class WelcomeModel {
    private String titulo;
    private String subtitulo;

    public WelcomeModel() {
        this.titulo = "¡Bienvenido al Sistema!";
        this.subtitulo = "Haz clic en el botón para ingresar.";
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }
}