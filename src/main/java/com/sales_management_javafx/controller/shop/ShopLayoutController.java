package com.sales_management_javafx.controller.shop;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShopLayoutController implements Initializable {
    @FXML
    private BorderPane shop_layout;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setTable();
        this.setToolbar();
    }
    public void setToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shopToolbar.fxml"));
        try {
            GridPane toolbar = toolbarLoader.load();
            shop_layout.setBottom(toolbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setTable(){
        FXMLLoader tableLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shopTable.fxml"));
        try {
            BorderPane table_borderpane = tableLoader.load();
            shop_layout.setCenter(table_borderpane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
