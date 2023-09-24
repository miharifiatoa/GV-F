package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.article.ArticleBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArticleEntity;

import java.io.IOException;
import java.util.Collection;

public class ArticleGridPane {
    private final GridPane gridPane;

    public ArticleGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<ArticleEntity> articles, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.setHgap(5);
            gridPane.setVgap(5);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ArticleEntity article : articles) {
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
    private StackPane getArticleBox(ArticleEntity article){
        FXMLLoader articleBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article/articleBox.fxml"));
        StackPane stackPane;
        try {
            stackPane = articleBoxLoader.load();
            ArticleBoxController articleBoxController = articleBoxLoader.getController();
            articleBoxController.initialize(article);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stackPane;
    }
}
