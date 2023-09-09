package com.sales_management_javafx.classes;

import javafx.scene.control.TextField;

public class NumberField {
    public NumberField() {
    }
    public void requireNumberOnly(TextField numberField){
        numberField.textProperty().addListener((observableValue, old, newValue) -> {
            if (!newValue.matches("\\d*")){
                numberField.setText(newValue.replaceAll("\\D",""));
            }
        });
    }
}
