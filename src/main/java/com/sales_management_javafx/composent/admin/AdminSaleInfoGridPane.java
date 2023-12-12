package com.sales_management_javafx.composent.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.admin.AdminArticleInfoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;
import org.sales_management.entity.ShareArticleEntity;
import org.sales_management.entity.ShareEntity;

import java.io.IOException;

public class AdminSaleInfoGridPane {
    private final GridPane gridPane;

    public AdminSaleInfoGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(SaleEntity sale, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (SaleArticleEntity saleArticle : sale.getSaleArticles()) {
            gridPane.add(this.getAdminArticleInfo(saleArticle), col, row);
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
    private VBox getAdminArticleInfo(SaleArticleEntity saleArticle){
        FXMLLoader adminArticleInfoLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/adminArticleInfo.fxml"));
        VBox adminArticleInfo;
        try {
            adminArticleInfo = adminArticleInfoLoader.load();
            AdminArticleInfoController adminArticleInfoController = adminArticleInfoLoader.getController();
            adminArticleInfoController.initialize(saleArticle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adminArticleInfo;
    }
}
