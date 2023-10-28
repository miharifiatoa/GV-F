package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.dashboard.DashboardMenuBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Map;

public class MenuGridPane {
    private final GridPane gridPane;
    public MenuGridPane(){
        this.gridPane = new GridPane();
    }
    public GridPane getGridPane(Map<String,String> menus, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (Map.Entry<String,String> entry : menus.entrySet()) {
            gridPane.add(this.getMenuBox(entry.getKey(), entry.getValue()), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("box-layout");
        return gridPane;
    }
    private StackPane getMenuBox(String menu , String icon_name){
        FXMLLoader menuBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/dashboard/dashboardMenuBox.fxml"));
        StackPane dashboardMenuBox;
        try {
            dashboardMenuBox = menuBoxLoader.load();
            DashboardMenuBoxController menuBoxController = menuBoxLoader.getController();
            menuBoxController.initialize(menu,icon_name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dashboardMenuBox;
    }
}
