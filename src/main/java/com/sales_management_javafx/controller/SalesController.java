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
    private StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/login/loginLayout.fxml"));
        try {
            BorderPane borderPane = fxmlLoader.load();
            this.stackPane.getChildren().add(borderPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}