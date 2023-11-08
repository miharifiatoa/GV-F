package com.sales_management_javafx.composent.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.admin.AdminSaleBoxController;
import com.sales_management_javafx.controller.sale.SaleBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.SaleEntity;

import java.io.IOException;
import java.util.Collection;

public class AdminSaleGridPane {
    private final GridPane gridPane;
    public AdminSaleGridPane() {
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
            gridPane.add(this.getAdminSaleBox(sale), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        gridPane.setId("sale");
        return gridPane;
    }
    private StackPane getAdminSaleBox(SaleEntity sale){
        FXMLLoader adminSaleBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/adminSaleBox.fxml"));
        StackPane adminSaleBox;
        try {
            adminSaleBox = adminSaleBoxLoader.load();
            AdminSaleBoxController adminSaleBoxController = adminSaleBoxLoader.getController();
            adminSaleBoxController.initialize(sale);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adminSaleBox;
    }
}
