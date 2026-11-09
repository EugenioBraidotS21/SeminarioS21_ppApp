package com.pandapickapp.model;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseModel<T> {
    protected static final String DB_URL = "jdbc:sqlite:Data/Pandita.db";

    protected List<T> items = new ArrayList<>();

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = (items != null) ? items : new ArrayList<>();
    }

    public void reset() {
        this.items = new ArrayList<>();
    }
}
