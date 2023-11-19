package com.sales_management_javafx.classes;

import java.time.LocalDateTime;
import java.util.Locale;

public class DateTimeFormatter {
    public static String format(LocalDateTime localDateTime){
        return localDateTime.format(java.time.format.DateTimeFormatter.ofPattern("EEEE d MMMM yyyy 'à' HH:mm:ss", Locale.FRENCH));
    }
}
