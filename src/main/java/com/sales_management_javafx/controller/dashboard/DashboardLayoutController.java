package com.sales_management_javafx.controller.dashboard;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.MenuIcon;
import com.sales_management_javafx.composent.MenuGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardLayoutController implements Initializable {
    @FXML private BorderPane dashboardLayout;
    @FXML private BorderPane dashboardLayoutBorderpane;
    private final MenuGridPane menuGridPane;
    private final MenuIcon menuIcon;

    public DashboardLayoutController() {
        this.menuIcon = new MenuIcon();
        this.menuGridPane = new MenuGridPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboardLayoutBorderpane.setTop(this.menuGridPane.getGridPane(menuIcon.getMenuIcons(),6));
        dashboardLayoutBorderpane.setBottom(getDashboardToolbar());
    }

    private StackPane getDashboardToolbar(){
        FXMLLoader dashboardToolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/dashboard/dashboardToolbar.fxml"));
        StackPane dashboardToolbar;
        try {
            dashboardToolbar = dashboardToolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dashboardToolbar;
    }
}
