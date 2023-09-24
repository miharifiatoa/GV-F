package com.sales_management_javafx.controller.dashboard;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.MenuIcon;
import com.sales_management_javafx.composent.MenuGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardLayoutController implements Initializable {
    @FXML
    private BorderPane dashboard;
    private final MenuGridPane menuGridPane;
    private final MenuIcon menuIcon;

    public DashboardLayoutController() {
        this.menuIcon = new MenuIcon();
        this.menuGridPane = new MenuGridPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboard.setLeft(this.menuGridPane.getGridPane(menuIcon.getMenuIcons(),1));
    }
}
