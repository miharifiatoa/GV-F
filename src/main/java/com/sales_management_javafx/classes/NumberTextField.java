package com.sales_management_javafx.classes;

import javafx.scene.control.TextField;

import java.util.concurrent.atomic.AtomicBoolean;

public class NumberTextField {
    public NumberTextField(){

    }
    public static void requireNumber(TextField number_field){
        number_field.textProperty().addListener((observableValue, old, newValue) -> {
            if (!newValue.matches("\\d*")){
                number_field.setText(newValue.replaceAll("\\D",""));
            }
        });
    }
    public static void requireIntegerOnly(TextField integer_field,int maxValue){
        integer_field.textProperty().addListener((observableValue, old, newValue) -> {
            if (!newValue.matches("\\d*")){
                integer_field.setText(newValue.replaceAll("\\D",""));
            }
            try {
                if (!newValue.isEmpty()){
                    int value = Integer.parseInt(newValue);
                    if (value>maxValue){
                        integer_field.setText(old);
                    }
                }
            } catch (NumberFormatException e) {
                integer_field.setText(old);
            }
        });
    }
    public static void requireDecimal(TextField decimal_field){
        decimal_field.textProperty().addListener((observableValue, old, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                decimal_field.setText(newValue.replaceAll("\\D",""));
            }
            try {
                Float value = Float.valueOf(newValue);
            } catch (NumberFormatException e) {
                decimal_field.setText(old);
            }
        });
    }
    public static void requireDouble(TextField double_field) {
        double_field.textProperty().addListener((observableValue, old, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                double_field.setText(old);
            } else {
                try {
                    if (!newValue.isEmpty()){
                        double doubleValue = Double.parseDouble(newValue);
                        if (Double.isInfinite(doubleValue)){
                            double_field.setText(old);
                        }
                    }
                } catch (NumberFormatException e) {
                    double_field.setText(old);
                }
            }
        });
    }
}
