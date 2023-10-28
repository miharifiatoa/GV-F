package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.article.ArticleInfoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleEntity;

import java.io.IOException;
import java.util.Collection;

public class ArrivalArticleGridPane {
    private final GridPane gridPane;

    public ArrivalArticleGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<ArticleEntity> articles, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ArticleEntity priceVariation : articles) {
            gridPane.add(this.getArticleShareBox(priceVariation), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        gridPane.setId("arrivalArticleGridpane");
        return gridPane;
    }
    private VBox getArticleShareBox(ArticleEntity article){
        FXMLLoader productShareListLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article/articleInfo.fxml"));
        VBox productShareList;
        try {
            productShareList = productShareListLoader.load();
            ArticleInfoController productSharedListController = productShareListLoader.getController();
            productSharedListController.initialize(article);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productShareList;
    }
}
