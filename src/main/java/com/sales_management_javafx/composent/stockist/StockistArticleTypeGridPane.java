package com.sales_management_javafx.composent.stockist;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.stockist.StockistArticleTypeBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import org.sales_management.entity.ArticleTypeEntity;

import java.io.IOException;
import java.util.Collection;

public class StockistArticleTypeGridPane {
    private final GridPane gridPane;

    public StockistArticleTypeGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<ArticleTypeEntity> articles, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ArticleTypeEntity article : articles) {
            gridPane.add(this.getBox(article), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getBox(ArticleTypeEntity article){
        FXMLLoader boxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/stockist/stockistArticleTypeBox.fxml"));
        StackPane box;
        try {
            box = boxLoader.load();
            StockistArticleTypeBoxController stockistArticleTypeBoxController = boxLoader.getController();
            stockistArticleTypeBoxController.initialize(article);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return box;
    }
}
