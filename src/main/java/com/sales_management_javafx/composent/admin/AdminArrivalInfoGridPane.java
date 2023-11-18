package com.sales_management_javafx.composent.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.admin.AdminArticleInfoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import org.sales_management.entity.ArrivalArticleEntity;
import org.sales_management.entity.ArrivalEntity;

import java.io.IOException;

public class AdminArrivalInfoGridPane {
    private final GridPane gridPane;

    public AdminArrivalInfoGridPane() {
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
            gridPane.add(this.getAdminArticleInfo(arrivalArticle), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private VBox getAdminArticleInfo(ArrivalArticleEntity arrivalArticle){
        FXMLLoader adminArticleInfoLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/adminArticleInfo.fxml"));
        VBox adminArticleInfo;
        try {
            adminArticleInfo = adminArticleInfoLoader.load();
            AdminArticleInfoController adminArticleInfoController = adminArticleInfoLoader.getController(); 
            adminArticleInfoController.initialize(arrivalArticle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adminArticleInfo;
    }
}
