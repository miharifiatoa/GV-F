package com.sales_management_javafx.controller.login;

import com.sales_management_javafx.SalesApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private GridPane login;
    private BorderPane borderPane;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/dashboard/dashboardLayout.fxml"));
        try {
            borderPane = fxmlLoader.load();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void login(ActionEvent actionEvent) {
        StackPane stackPane = (StackPane) login.getParent().getParent();
        stackPane.getChildren().remove(0);
        stackPane.getChildren().add(borderPane);
    }
}
