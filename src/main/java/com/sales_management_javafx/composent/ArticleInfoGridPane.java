package com.sales_management_javafx.composent;
import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.article_type.ArticleTypeInfoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import org.sales_management.entity.ArticleTypeEntity;

import java.io.IOException;
import java.util.Collection;

public class ArticleInfoGridPane {
    private final GridPane gridPane;

    public ArticleInfoGridPane() {
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
            gridPane.add(this.getArticleShareBox(priceVariation), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        gridPane.setId("articleShareGridpane");
        return gridPane;
    }
    private VBox getArticleShareBox(ArticleTypeEntity articleType){
        FXMLLoader productShareListLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article_type/articleInfo.fxml"));
        VBox productShareList;
        try {
            productShareList = productShareListLoader.load();
            ArticleTypeInfoController productSharedListController = productShareListLoader.getController();
            productSharedListController.initialize(articleType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productShareList;
    }
}
