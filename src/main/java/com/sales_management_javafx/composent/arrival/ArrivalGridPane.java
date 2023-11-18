package com.sales_management_javafx.composent.arrival;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.admin.AdminArrivalBoxController;
import com.sales_management_javafx.controller.arrival.ArrivalBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArrivalEntity;

import java.io.IOException;
import java.util.Collection;

public class ArrivalGridPane {
    private final GridPane gridPane;
    public ArrivalGridPane() {
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
            gridPane.add(this.getArrivalBox(arrival), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        gridPane.setId("product-type-grid-pane");
        return gridPane;
    }
    private StackPane getArrivalBox(ArrivalEntity arrival){
        FXMLLoader arrivalBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/arrival/arrivalBox.fxml"));
        StackPane arrivalBox;
        try {
            arrivalBox = arrivalBoxLoader.load();
            ArrivalBoxController arrivalBoxController = arrivalBoxLoader.getController();
            arrivalBoxController.initializeForStockist(arrival);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return arrivalBox;
    }
}
