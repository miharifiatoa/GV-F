package com.sales_management_javafx.composent.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.admin.AdminArrivalBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import org.sales_management.entity.ArrivalEntity;

import java.io.IOException;
import java.util.Collection;

public class AdminArrivalGridPane {
    private final GridPane gridPane;
    public AdminArrivalGridPane() {
        this.gridPane = new GridPane();
    }
    public GridPane getGridPane(Collection<ArrivalEntity> arrivals , int colSize) {
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ArrivalEntity arrival : arrivals) {
            gridPane.add(this.getAdminArrivalBox(arrival), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        gridPane.setId("arrival");
        return gridPane;
    }
    private StackPane getAdminArrivalBox(ArrivalEntity arrival){
        FXMLLoader adminSaleBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/adminArrivalBox.fxml"));
        StackPane adminSaleBox;
        try {
            adminSaleBox = adminSaleBoxLoader.load();
            AdminArrivalBoxController adminArrivalBoxController = adminSaleBoxLoader.getController();
            adminArrivalBoxController.initialize(arrival);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adminSaleBox;
    }
}
