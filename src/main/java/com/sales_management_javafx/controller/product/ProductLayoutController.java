package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductLayoutController implements Initializable {
    @FXML
    private BorderPane articleLayoutBorderpane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.articleLayoutBorderpane.setCenter(this.getTable());
        this.articleLayoutBorderpane.setBottom(this.getToolbar());
    }
    public GridPane getToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productToolbar.fxml"));
        GridPane toolbar;
        try {
            toolbar = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toolbar;
    }
    public BorderPane getTable(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productTable.fxml"));
        BorderPane table;
        try {
            table = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return table;
    }
}
