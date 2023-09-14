package com.sales_management_javafx.classes;

import javafx.scene.control.TextField;

public class NumberField {
    public NumberField(){

    }
    public static void requireIntegerOnly(TextField integer_field){
        integer_field.textProperty().addListener((observableValue, old, newValue) -> {
            if (!newValue.matches("\\d*")){
                integer_field.setText(newValue.replaceAll("\\D",""));
            }
        });
    }
    public static void requireDecimal(TextField decimal_field){
        decimal_field.textProperty().addListener((observableValue, old, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                decimal_field.setText(old); // Rétablir la valeur précédente si la nouvelle valeur n'est pas valide
            }
        });
    }
}
