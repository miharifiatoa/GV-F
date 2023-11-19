package com.sales_management_javafx.controller.shop;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.ShopGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.sales_management.service.ShopService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShopLayoutController implements Initializable {
    @FXML private ScrollPane shopBoxLayoutScrollpane;
    @FXML private BorderPane shopLayout;
    private final ShopService shopService;

    public ShopLayoutController() {
        this.shopService = new ShopService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setShops();
        this.setToolbar();
    }
    public void setToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shopToolbar.fxml"));
        try {
            GridPane toolbar = toolbarLoader.load();
            shopLayout.setBottom(toolbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setShops(){
        GridPane shopGridPane = new ShopGridPane().getGridPane(shopService.getAll(),4);
        shopBoxLayoutScrollpane.setContent(shopGridPane);
    }
}
