package com.sales_management_javafx.controller.login;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginLayoutController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/login/login.fxml"));
        try {
            GridPane gridPane = fxmlLoader.load();
            this.borderPane.setCenter(gridPane);
            BorderPane.setAlignment(gridPane, Pos.TOP_CENTER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
