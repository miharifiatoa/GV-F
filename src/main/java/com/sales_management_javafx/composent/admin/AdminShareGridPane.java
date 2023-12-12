package com.sales_management_javafx.composent.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.admin.AdminShareBoxController;
import com.sales_management_javafx.controller.share.ShareBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ShareEntity;

import java.io.IOException;
import java.util.Collection;

public class AdminShareGridPane {
    private final GridPane gridPane;
    public AdminShareGridPane() {
        this.gridPane = new GridPane();
    }
    public GridPane getGridPane(Collection<ShareEntity> shares , int colSize) {
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ShareEntity share : shares) {
            gridPane.add(this.getShareBox(share), col, row);
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
    private StackPane getShareBox(ShareEntity share){
        FXMLLoader shareBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/share/shareBox.fxml"));
        StackPane shareBox;
        try {
            shareBox = shareBoxLoader.load();
            ShareBoxController shareBoxController = shareBoxLoader.getController();
            shareBoxController.initializeForAdmin(share);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return shareBox;
    }
}
