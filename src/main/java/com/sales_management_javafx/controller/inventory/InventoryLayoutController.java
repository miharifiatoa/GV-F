package com.sales_management_javafx.controller.inventory;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryLayoutController implements Initializable {
    @FXML
    private BorderPane inventoryLayoutBorderpane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putArticleLayout();
    }
    private void putArticleLayout(){
        FXMLLoader loadArticleLayout = new FXMLLoader(SalesApplication.class.getResource("fxml/inventory/articleLayout.fxml"));
        try {
            BorderPane articleLayout = loadArticleLayout.load();
            this.inventoryLayoutBorderpane.setCenter(articleLayout);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
