package com.sales_management_javafx.composent.stockist;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.seller.SellerArticleBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArticleEntity;

import java.io.IOException;
import java.util.Collection;

public class StockistArticleGridPane {
    private final GridPane gridPane;

    public StockistArticleGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<ArticleEntity> articleEntities, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ArticleEntity article : articleEntities) {
            gridPane.add(this.getSellerArticleBox(article), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getSellerArticleBox(ArticleEntity article){
        FXMLLoader sellerArticleBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/sellerArticleBox.fxml"));
        StackPane sellerArticleBox;
        try {
            sellerArticleBox = sellerArticleBoxLoader.load();
            SellerArticleBoxController sellerArticleBoxController = sellerArticleBoxLoader.getController();
            sellerArticleBoxController.initializeForStockist(article);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sellerArticleBox;
    }
}
