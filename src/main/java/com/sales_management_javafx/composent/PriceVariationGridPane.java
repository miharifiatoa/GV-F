package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.controller.product_variation.ProductVariationBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.PriceVariationEntity;

import java.io.IOException;
import java.util.Collection;

public class PriceVariationGridPane {
    private final GridPane gridPane;

    public PriceVariationGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<PriceVariationEntity> priceVariations, int colSize, boolean show){
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
        if (show){
            gridPane.add(this.getCreatePriceBox(),col,row);
            col++;
        }
        for (PriceVariationEntity priceVariation : priceVariations) {
            if (!FileIO.readPricesFromFile("prices.dat").contains(priceVariation)){
                gridPane.add(this.getProductBox(priceVariation), col, row);
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
    private StackPane getProductBox(PriceVariationEntity priceVariation){
        FXMLLoader productVariationBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_variation/productVariationBox.fxml"));
        StackPane productBoxStackpane;
        try {
            productBoxStackpane = productVariationBoxLoader.load();
            ProductVariationBoxController productVariationBoxController = productVariationBoxLoader.getController();
            productVariationBoxController.initialize(priceVariation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productBoxStackpane;
    }
    private StackPane getCreatePriceBox(){
        FXMLLoader createPriceBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_variation/priceVariationCreate.fxml"));
        StackPane priceVariationCreateStackPane;
        try {
            priceVariationCreateStackPane = createPriceBoxLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return priceVariationCreateStackPane;
    }
}
