package com.erp.provanivel3.domain.enums;

public enum StatusPedido {
    ABERTO(1, "aberto"),
    FECHADO(2, "fechado");

    private int cod;
    private String description;

    StatusPedido(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public static StatusPedido toEnum(Integer cod) {

        if (cod == null) return null;

        for (StatusPedido sp : StatusPedido.values()) {
            if (cod.equals(sp.getCod())) return sp;
        }
        throw new IllegalArgumentException("Cod is not valid: " + cod);
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
