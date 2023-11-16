package com.sales_management_javafx.composent.share;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.article_type.ArticleCodeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import org.sales_management.entity.ShareArticleEntity;
import org.sales_management.entity.ShareEntity;

import java.io.IOException;

public class ShareInfoGridPane {
    private final GridPane gridPane;

    public ShareInfoGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(ShareEntity share, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ShareArticleEntity shareArticle : share.getShareArticles()) {
            gridPane.add(this.getArticleCode(shareArticle), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("box");
        return gridPane;
    }
    private StackPane getArticleCode(ShareArticleEntity shareArticle){
        FXMLLoader articleCodeLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article/articleCode.fxml"));
        StackPane articleCode;
        try {
            articleCode = articleCodeLoader.load();
            ArticleCodeController articleCodeController = articleCodeLoader.getController();
            articleCodeController.initialize(shareArticle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return articleCode;
    }
}
