package com.sales_management_javafx.controller.shop;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShopToolbarController implements Initializable {
    @FXML
    private GridPane shopToolbarGridpane;
    @FXML
    private Button createShopButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onCreateShop();
    }
    public void onCreateShop(){
        createShopButton.setOnAction(event->{
            FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shopForm.fxml"));
            try {
                BorderPane shopLayoutBorderpane = (BorderPane) shopToolbarGridpane.getParent();
                VBox userForm = fxmlLoader.load();
                shopLayoutBorderpane.setBottom(userForm);
            } catch (IOException e) {
                System.out.println("Error: error while loading user userForm.fxml");
            }
        });
    }
}
