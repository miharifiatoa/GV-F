package com.sales_management_javafx.composent.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.admin.historyBoxController;
import com.sales_management_javafx.controller.product.ProductBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ProductEntity;
import org.sales_management.entity.StockHistoryEntity;

import java.io.IOException;
import java.util.Collection;

public class AdminStoryGridPane {
    private final GridPane gridPane;

    public AdminStoryGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<StockHistoryEntity> stockHistoryEntities, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (StockHistoryEntity history : stockHistoryEntities) {
            gridPane.add(this.getAdminArticleHistoryListe(history), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getAdminArticleHistoryListe(StockHistoryEntity history){
        FXMLLoader historyBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/historyBox.fxml"));
        StackPane historyBox;
        try {
            historyBox = historyBoxLoader.load();
            historyBoxController historyBoxController = historyBoxLoader.getController();
            historyBoxController.initialize(history);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return historyBox;
    }
}
