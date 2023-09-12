package com.sales_management_javafx.controller.dashboard;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardLayoutController implements Initializable {
    @FXML
    private BorderPane dashboard;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboard.setLeft(getMenuBar());
    }
    public BorderPane getMenuBar(){
        FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/dashboard/dashboardMenu.fxml"));
        BorderPane menuBar;
        try {
            menuBar = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return menuBar;
    }
}
