package com.sales_management_javafx.composent;
import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.product.ProductSharedListController;
import com.sales_management_javafx.controller.product_variation.PriceVariationSharedListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import org.sales_management.entity.PriceVariationEntity;
import org.sales_management.entity.ProductEntity;

import java.io.IOException;
import java.util.Collection;

public class ProductShareGridPane {
    private final GridPane gridPane;

    public ProductShareGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<PriceVariationEntity> priceVariations, int colSize){
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
        for (PriceVariationEntity priceVariation : priceVariations) {
            gridPane.add(this.getProductShareList(priceVariation), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private VBox getProductShareList(PriceVariationEntity priceVariation){
        FXMLLoader productShareListLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_variation/priceVariationSharedList.fxml"));
        VBox productShareList;
        try {
            productShareList = productShareListLoader.load();
            PriceVariationSharedListController productSharedListController = productShareListLoader.getController();
            productSharedListController.initialize(priceVariation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productShareList;
    }
}
