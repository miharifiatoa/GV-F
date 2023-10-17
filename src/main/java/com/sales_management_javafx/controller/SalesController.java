package com.sales_management_javafx.controller;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SalesController implements Initializable {
    @FXML
    private BorderPane salesManagementBorderpane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/login/login.fxml"));
        try {
            GridPane loginGridpane = fxmlLoader.load();
            this.salesManagementBorderpane.setCenter(loginGridpane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}