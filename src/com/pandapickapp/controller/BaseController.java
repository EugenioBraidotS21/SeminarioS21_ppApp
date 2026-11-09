package com.pandapickapp.controller;

import com.pandapickapp.model.BaseModel;
import com.pandapickapp.view.BaseView;

import javafx.stage.Stage;

public abstract class BaseController<M extends BaseModel<?>, V extends BaseView<?>> {
    protected M model;
    protected V view;
    protected Stage stage;

    protected BaseController(M model, V view, Stage stage) {
        this.model = model;
        this.view = view;
        this.stage = stage;
    }

    protected abstract void inicializar();

    public void dispose() {
        if (view != null) {
            view.limpiarVista();
        }
        if (model != null) {
            model.reset();
        }
        model = null;
        view = null;
        stage = null;
    }
}
