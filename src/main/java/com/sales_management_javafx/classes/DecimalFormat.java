package com.sales_management_javafx.classes;

public class DecimalFormat {
    public static String format(Double decimal){
        java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("#,##0.00");
        return decimalFormat.format(decimal);
    }
}
