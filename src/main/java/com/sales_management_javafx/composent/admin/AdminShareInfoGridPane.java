package com.sales_management_javafx.composent.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.admin.AdminArticleInfoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.sales_management.entity.*;

import java.io.IOException;

public class AdminShareInfoGridPane {
    private final GridPane gridPane;

    public AdminShareInfoGridPane() {
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
            gridPane.add(this.getAdminArticleInfo(shareArticle), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }
    private VBox getAdminArticleInfo(ShareArticleEntity shareArticle){
        FXMLLoader adminArticleInfoLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/adminArticleInfo.fxml"));
        VBox adminArticleInfo;
        try {
            adminArticleInfo = adminArticleInfoLoader.load();
            AdminArticleInfoController adminArticleInfoController = adminArticleInfoLoader.getController();
            adminArticleInfoController.initialize(shareArticle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adminArticleInfo;
    }
}
