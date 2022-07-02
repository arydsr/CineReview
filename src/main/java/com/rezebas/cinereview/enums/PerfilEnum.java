package com.rezebas.cinereview.enums;

public enum PerfilEnum {

    PERFIL_LEITOR("LEITOR"),
    PERFIL_BASICO("BASICO"),
    PERFIL_AVANCADO("AVANCADO"),
    PERFIL_MODERADOR("MODERADOR");

    private final String value;

    PerfilEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
