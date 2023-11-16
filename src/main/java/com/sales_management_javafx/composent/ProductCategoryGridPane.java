package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.product.ProductCategoryBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ProductEntity;

import java.io.IOException;
import java.util.Collection;

public class ProductCategoryGridPane {
    private final GridPane gridPane;

    public ProductCategoryGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<ProductEntity> articles, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ProductEntity article : articles) {
            gridPane.add(this.getArticleBox(article), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getArticleBox(ProductEntity article){
        FXMLLoader articleBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_category/productCategoryBox.fxml"));
        StackPane stackPane;
        try {
            stackPane = articleBoxLoader.load();
            ProductCategoryBoxController productCategoryBoxController = articleBoxLoader.getController();
            productCategoryBoxController.initialize(article);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stackPane;
    }
}
