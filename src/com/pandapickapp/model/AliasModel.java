package com.pandapickapp.model;

public class AliasModel extends BaseModel<Void> {
    private String alias;
    public static final int MAX_CARACTERES = 10;

    public AliasModel() {
        this.alias = "";
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = (alias != null) ? alias.trim() : "";
    }

    @Override
    public void reset() {
        super.reset();
        this.alias = "";
    }

    /**
     * Valida el alias ingresado.
     * @return null si es válido, o el mensaje de error correspondiente.
     */
    public String validar(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return "El alias no puede estar vacío.";
        }
        if (texto.trim().length() > MAX_CARACTERES) {
            return "El alias no puede superar los " + MAX_CARACTERES + " caracteres.";
        }
        return null;
    }
}
