package com.sales_management_javafx.composent.arrival;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.article_type.ArticleCodeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArrivalArticleEntity;
import org.sales_management.entity.ArrivalEntity;

import java.io.IOException;

public class ArrivalInfoGridPane {
    private final GridPane gridPane;

    public ArrivalInfoGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(ArrivalEntity arrival, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ArrivalArticleEntity arrivalArticle : arrival.getArrivalArticles()) {
            gridPane.add(this.getArticleCode(arrivalArticle), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("box");
        return gridPane;
    }
    private StackPane getArticleCode(ArrivalArticleEntity arrivalArticle){
        FXMLLoader articleCodeLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article/articleCode.fxml"));
        StackPane articleCode;
        try {
            articleCode = articleCodeLoader.load();
            ArticleCodeController articleCodeController = articleCodeLoader.getController();
            articleCodeController.initialize(arrivalArticle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return articleCode;
    }
}
