package com.sales_management_javafx.classes;

import java.util.HashMap;
import java.util.Map;

public class MenuIcon {
    private final Map<String,String> menuIcons;

    public MenuIcon() {
        this.menuIcons = new HashMap<>();
    }

    public Map<String, String> getMenuIcons() {
        menuIcons.put("ACCOUNT" , "AccountIcon.png");
        menuIcons.put("SHOP" , "ShopIcon.png");
        menuIcons.put("SALE" , "SaleIcon.png");
        menuIcons.put("ARRIVAL" , "ArrivalIcon.png");
        menuIcons.put("STOCK" , "InventoryIcon.png");
        return menuIcons;
    }
}
