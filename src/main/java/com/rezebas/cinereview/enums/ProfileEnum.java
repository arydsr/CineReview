package com.rezebas.cinereview.enums;

public enum ProfileEnum {

    ROLE_LEITOR("LEITOR"),
    ROLE_BASICO("BASICO"),
    ROLE_AVANCADO("AVANCADO"),
    ROLE_MODERADOR("MODERADOR");

    private final String value;

    ProfileEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
