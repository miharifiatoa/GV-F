package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.seller.SellerProductBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ProductEntity;

import java.io.IOException;
import java.util.Collection;

public class SellerProductGridPane {
    private final GridPane gridPane;

    public SellerProductGridPane() {
        this.gridPane = new GridPane();
    }
    public GridPane getGridPane(Collection<ProductEntity> products, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ProductEntity product : products) {
            gridPane.add(this.getSellerProductBox(product), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getSellerProductBox(ProductEntity product){
        FXMLLoader productBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/sellerProductBox.fxml"));
        StackPane stackPane;
        try {
            stackPane = productBoxLoader.load();
            SellerProductBoxController sellerProductBoxController = productBoxLoader.getController();
            sellerProductBoxController.initialize(product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stackPane;
    }
}
