package com.sales_management_javafx.controller.inventory;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryLayoutController implements Initializable {
    @FXML
    private BorderPane inventoryLayoutBorderpane;
    @FXML
    private Button exitFromInventoryButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putProductBoxLayout();
        this.onExitFromInventoryButton();
    }
    private void putProductBoxLayout(){
        FXMLLoader productBoxLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productBoxLayout.fxml"));
        try {
            BorderPane productBoxLayout = productBoxLayoutLoader.load();
            this.inventoryLayoutBorderpane.setCenter(productBoxLayout);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void onExitFromInventoryButton(){
        exitFromInventoryButton.setOnAction(event->{
            BorderPane dashboard = (BorderPane) this.inventoryLayoutBorderpane.getParent();
            dashboard.setLeft(this.getMenuBar());
            dashboard.setCenter(null);
        });
    }
    private BorderPane getMenuBar(){
        BorderPane borderPane;
        FXMLLoader menuBarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/dashboard/dashboardMenu.fxml"));
        try {
            borderPane = menuBarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return borderPane;
    }
}
