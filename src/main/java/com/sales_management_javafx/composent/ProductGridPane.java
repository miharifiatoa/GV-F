package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.product.ProductBoxController;
import com.sales_management_javafx.controller.product_type.ProductTypeBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ProductEntity;

import java.io.IOException;
import java.util.Collection;

public class ProductGridPane {
    private final GridPane gridPane;

    public ProductGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<ProductEntity> productEntities, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ProductEntity product : productEntities) {
            gridPane.add(this.getProductBox(product), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getProductBox(ProductEntity product){
        FXMLLoader articleBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productBox.fxml"));
        StackPane stackPane;
        try {
            stackPane = articleBoxLoader.load();
            ProductBoxController productBoxController = articleBoxLoader.getController();
            productBoxController.initialize(product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stackPane;
    }
}
