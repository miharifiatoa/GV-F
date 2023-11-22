package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.product_category.ProductCategoryBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ProductCategoryEntity;

import java.io.IOException;
import java.util.Collection;

public class ProductCategoryGridPane {
    private final GridPane gridPane;

    public ProductCategoryGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<ProductCategoryEntity> productCategoryEntities, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        if(productCategoryEntities!=null){
        for (ProductCategoryEntity productCategory : productCategoryEntities) {
            gridPane.add(this.getProductCategoryBox(productCategory), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }}
        gridPane.getStyleClass().add("gridpane");
     
        return gridPane;
    }
    private StackPane getProductCategoryBox(ProductCategoryEntity productCategory){
        FXMLLoader productCategoryBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_category/productCategoryBox.fxml"));
        StackPane stackPane;
        try {
            stackPane = productCategoryBoxLoader.load();
            ProductCategoryBoxController productCategoryBoxController = productCategoryBoxLoader.getController();
            productCategoryBoxController.initialize(productCategory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stackPane;
    }
}
