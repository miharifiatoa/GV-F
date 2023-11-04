package com.sales_management_javafx.controller.dashboard;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardToolbarController implements Initializable {
    @FXML private StackPane dashboardToolbar;
    @FXML private ImageView newAccountIcon;
    @FXML private ImageView newShopIcon;
    @FXML private Button newAccount;
    @FXML private Button newShop;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setNewShop();
        this.setNewAccount();
        this.putIcons();
    }
    private void setNewShop(){
        newShop.setOnAction(event->{
            BorderPane dashboardLayout = (BorderPane) dashboardToolbar.getParent();
            dashboardLayout.setBottom(getShopForm());
        });
    }
    private void setNewAccount(){
        newAccount.setOnAction(event->{
            BorderPane dashboardLayout = (BorderPane) dashboardToolbar.getParent();
            dashboardLayout.setBottom(getUserForm());
        });
    }
    private void putIcons(){
        this.newAccountIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/NewAccountIcon.png"))));
        this.newShopIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/NewShopIcon.png"))));
    }
    private VBox getShopForm(){
        FXMLLoader shopFormLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shopForm.fxml"));
        VBox shopForm;
        try {
            shopForm = shopFormLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return shopForm;
    }
    private VBox getUserForm(){
        FXMLLoader userFormLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/user/userForm.fxml"));
        VBox userForm;
        try {
            userForm = userFormLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userForm;
    }
}
