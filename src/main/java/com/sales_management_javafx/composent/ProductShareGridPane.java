package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.product.ProductSharedListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import org.sales_management.entity.ProductEntity;

import java.io.IOException;
import java.util.Collection;

public class ProductShareGridPane {
    private final GridPane gridPane;

    public ProductShareGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<ProductEntity> products, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.setHgap(5);
            gridPane.setVgap(5);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ProductEntity product : products) {
            gridPane.add(this.getProductShareList(product), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private VBox getProductShareList(ProductEntity product){
        FXMLLoader productShareListLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productSharedList.fxml"));
        VBox productShareList;
        try {
            productShareList = productShareListLoader.load();
            ProductSharedListController productSharedListController = productShareListLoader.getController();
            productSharedListController.initialize(product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productShareList;
    }
}
