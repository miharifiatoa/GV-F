package com.sales_management_javafx.controller.seller;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SellerToolbarController implements Initializable {
    @FXML
    private GridPane pannierToolbar;
    @FXML
    private Button showPannierButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onShowPannier();
    }
    private void onShowPannier(){
        showPannierButton.setOnAction(event->{
            BorderPane sellerLayout = (BorderPane) pannierToolbar.getParent();
            sellerLayout.setBottom(getPannierLayout());
        });
    }
    private BorderPane getPannierLayout(){
        FXMLLoader pannierLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/pannierLayout.fxml"));
        BorderPane pannierLayout;
        try {
            pannierLayout = pannierLayoutLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pannierLayout;
    }
}
