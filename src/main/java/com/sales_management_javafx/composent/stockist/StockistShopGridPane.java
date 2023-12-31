package com.sales_management_javafx.composent.stockist;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.shop.ShopBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ShopEntity;

import java.io.IOException;
import java.util.Collection;

public class StockistShopGridPane {
    private final GridPane gridPane;

    public StockistShopGridPane() {
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
            if (shop.getId() != 1){
                gridPane.add(this.getShopBox(shop), col, row);
                col++;
                if (col == colSize) {
                    col = 0;
                    row++;
                }
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getShopBox(ShopEntity shop){
        FXMLLoader shopBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shopBox.fxml"));
        StackPane stackPane;
        try {
            stackPane = shopBoxLoader.load();
            ShopBoxController shopBoxController = shopBoxLoader.getController();
            shopBoxController.initializeForStockist(shop);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stackPane;
    }
}
