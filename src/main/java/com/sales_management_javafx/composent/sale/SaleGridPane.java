package com.sales_management_javafx.composent.sale;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.sale.SaleBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.SaleEntity;

import java.io.IOException;
import java.util.Collection;

public class SaleGridPane {
    private final GridPane gridPane;
    public SaleGridPane() {
        this.gridPane = new GridPane();
    }
    public GridPane getGridPane(Collection<SaleEntity> sales , int colSize) {
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (SaleEntity sale : sales) {
            gridPane.add(this.getSaleBox(sale), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
            System.out.println(sale.getPayed());
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getSaleBox(SaleEntity sale){
        FXMLLoader saleBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/sale/saleBox.fxml"));
        StackPane saleBox;
        try {
            saleBox = saleBoxLoader.load();
            SaleBoxController saleBoxController = saleBoxLoader.getController();
            saleBoxController.initializeForSale(sale);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return saleBox;
    }
}
