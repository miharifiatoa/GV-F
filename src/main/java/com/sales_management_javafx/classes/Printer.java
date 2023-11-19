package com.sales_management_javafx.classes;

import javafx.print.PrinterJob;
import javafx.scene.Node;

public class Printer {
    public static void print(Node node) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(null)) {
            boolean success = printerJob.printPage(node);
            if (success) {
                printerJob.endJob();
            }
        }
    }
}
