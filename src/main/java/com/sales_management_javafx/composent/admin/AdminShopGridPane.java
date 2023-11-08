package com.sales_management_javafx.composent.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.admin.AdminShopBoxController;
import com.sales_management_javafx.controller.shop.ShopBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ShopEntity;

import java.io.IOException;
import java.util.Collection;

public class AdminShopGridPane {
    private final GridPane gridPane;

    public AdminShopGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<ShopEntity> shops, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ShopEntity shop : shops) {
            gridPane.add(this.getAdminShopBox(shop), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getAdminShopBox(ShopEntity shop){
        FXMLLoader adminShopBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/adminShopBox.fxml"));
        StackPane adminShopBox;
        try {
            adminShopBox = adminShopBoxLoader.load();
            AdminShopBoxController adminShopBoxController = adminShopBoxLoader.getController();
            adminShopBoxController.initialize(shop);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adminShopBox;
    }
}
