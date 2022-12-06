package com.erp.provanivel3.domain.enums;

public enum CondicaoProduto {
    ATIVADO(1, "ativado"),
    DESATIVADO(2, "desativado");

    private int cod;
    private String description;

    CondicaoProduto(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public static CondicaoProduto toEnum(Integer cod) {

        if (cod == null) return null;

        for (CondicaoProduto sp : CondicaoProduto.values()) {
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
