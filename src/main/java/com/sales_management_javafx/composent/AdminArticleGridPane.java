package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.admin.AdminArrivalBoxController;
import com.sales_management_javafx.controller.admin.AdminArticleBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import org.sales_management.entity.ArrivalEntity;
import org.sales_management.entity.ArticleEntity;

import java.io.IOException;
import java.util.Collection;

public class AdminArticleGridPane {
    private final GridPane gridPane;
    public AdminArticleGridPane() {
        this.gridPane = new GridPane();
    }
    public GridPane getGridPane(Collection<ArticleEntity> articles , int colSize) {
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ArticleEntity article : articles) {
            gridPane.add(this.getAdminArticleBox(article), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        gridPane.setId("product-type-grid-pane");
        return gridPane;
    }
    private VBox getAdminArticleBox(ArticleEntity article){
        FXMLLoader adminArticleBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/adminArticleBox.fxml"));
        VBox adminArticleBox;
        try {
            adminArticleBox = adminArticleBoxLoader.load();
            AdminArticleBoxController adminArticleBoxController = adminArticleBoxLoader.getController();
            adminArticleBoxController.initialize(article);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adminArticleBox;
    }
}
