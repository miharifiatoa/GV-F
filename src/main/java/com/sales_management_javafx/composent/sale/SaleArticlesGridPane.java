package com.sales_management_javafx.composent.sale;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.admin.AdminArticleStoryBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import org.sales_management.entity.SaleArticleEntity;

import java.io.IOException;
import java.util.Collection;

public class SaleArticlesGridPane {
    private final GridPane gridPane;
    public SaleArticlesGridPane() {
        this.gridPane = new GridPane();
    }
    public GridPane getGridPane(Collection<SaleArticleEntity> saleArticles, int colSize) {
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (SaleArticleEntity saleArticle : saleArticles) {
            gridPane.add(getAdminStoryBox(saleArticle), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("box");
        return gridPane;
    }
    private StackPane getAdminStoryBox(SaleArticleEntity saleArticle){
        FXMLLoader adminStoryBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/adminArticleStoryBox.fxml"));
        StackPane adminStoryBox;
        try {
            adminStoryBox = adminStoryBoxLoader.load();
            AdminArticleStoryBoxController adminArticleStoryBoxController = adminStoryBoxLoader.getController();
            adminArticleStoryBoxController.initialize(saleArticle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adminStoryBox;
    }
}
