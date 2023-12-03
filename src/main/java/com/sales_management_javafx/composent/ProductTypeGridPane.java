package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.product_type.ProductTypeBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import org.sales_management.entity.ProductTypeEntity;

import java.io.IOException;
import java.util.Collection;

public class ProductTypeGridPane {
    private final GridPane gridPane;

    public ProductTypeGridPane() {
        this.gridPane = new GridPane();
    }
    public GridPane getGridPane(Collection<ProductTypeEntity> productTypes, int colSize, boolean isShowCreateBox){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ProductTypeEntity productType : productTypes) {
            gridPane.add(this.getProductBox(productType), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }
    private StackPane getProductBox(ProductTypeEntity product){
        FXMLLoader productBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_type/productTypeBox.fxml"));
        StackPane productBoxStackpane;
        try {
            productBoxStackpane = productBoxLoader.load();
            ProductTypeBoxController productTypeBoxController = productBoxLoader.getController();
            productTypeBoxController.initialize(product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productBoxStackpane;
    }
}
