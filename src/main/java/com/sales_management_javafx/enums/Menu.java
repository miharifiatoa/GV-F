package com.sales_management_javafx.enums;

public enum Menu {
    ACCOUNT("Comptes"),
    SHOP("Boutiques"),
    ARTICLE("Articles"),
    PRODUCT("Produits"),
    SALE("Ventes");
    private final String value;

    Menu(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
