package com.sales_management_javafx.composent.arrival;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.admin.AdminArticleStoryBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArrivalArticleEntity;

import java.io.IOException;
import java.util.Collection;

public class ArrivalArticlesGridPane {
    private final GridPane gridPane;
    public ArrivalArticlesGridPane() {
        this.gridPane = new GridPane();
    }
    public GridPane getGridPane(Collection<ArrivalArticleEntity> arrivalArticles, int colSize) {
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ArrivalArticleEntity arrivalArticle : arrivalArticles) {
            gridPane.add(getAdminStoryBox(arrivalArticle), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getAdminStoryBox(ArrivalArticleEntity arrivalArticle){
        FXMLLoader adminStoryBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/adminArticleStoryBox.fxml"));
        StackPane adminStoryBox;
        try {
            adminStoryBox = adminStoryBoxLoader.load();
            AdminArticleStoryBoxController adminArticleStoryBoxController = adminStoryBoxLoader.getController();
            adminArticleStoryBoxController.initialize(arrivalArticle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adminStoryBox;
    }
}
