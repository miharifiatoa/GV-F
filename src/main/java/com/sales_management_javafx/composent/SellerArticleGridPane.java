package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.seller.SellerArticleBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArticleTypeEntity;

import java.io.IOException;
import java.util.Collection;

public class SellerArticleGridPane {
    private final GridPane gridPane;

    public SellerArticleGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<ArticleTypeEntity> priceVariations, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ArticleTypeEntity priceVariation : priceVariations) {
            gridPane.add(this.getProductBox(priceVariation), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getProductBox(ArticleTypeEntity priceVariation){
        FXMLLoader productVariationBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/sellerArticleBox.fxml"));
        StackPane productBoxStackpane;
        try {
            productBoxStackpane = productVariationBoxLoader.load();
            SellerArticleBoxController sellerArticleBoxController = productVariationBoxLoader.getController();
            sellerArticleBoxController.initialize(priceVariation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productBoxStackpane;
    }
}
