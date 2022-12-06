package com.erp.provanivel3.domain.enums;

public enum TipoCatalogo {
    SERVICO(1, "servico"),
    PRODUTO(2, "produto");

    private int cod;
    private String description;

    TipoCatalogo(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public static TipoCatalogo toEnum(Integer cod) {

        if (cod == null) return null;

        for (TipoCatalogo tp : TipoCatalogo.values()) {
            if (cod.equals(tp.getCod())) return tp;
        }
        throw new IllegalArgumentException("Cod is not valid: " + cod);
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return description;
    }

    public void setDescricao(String description) {
        this.description = description;
    }

}
