package com.sales_management_javafx.controller.login;

import com.sales_management_javafx.SalesApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private GridPane loginGridpane;
    @FXML
    private Button connectionButton;
    @FXML
    private TextField passwordTextfield;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.connect();
    }

    public void connect() {
        connectionButton.setOnAction(event->{
            BorderPane salesManagementBorderpane = (BorderPane) loginGridpane.getParent();
            if (Objects.equals(passwordTextfield.getText(), "v")){
                salesManagementBorderpane.setCenter(this.getSellerLayout());
            }
            if (Objects.equals(passwordTextfield.getText(), "s")){
                salesManagementBorderpane.setCenter(this.getStockistLayout());
            }
            else {
                salesManagementBorderpane.setCenter(this.getDashboard());
            }
        });
    }
    private BorderPane getDashboard(){
        FXMLLoader dashboardLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/dashboard/dashboardLayout.fxml"));
        BorderPane dashboard;
        try {
            dashboard = dashboardLoader.load();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dashboard;
    }
    private BorderPane getSellerLayout(){
        FXMLLoader sellerLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/sellerLayout.fxml"));
        BorderPane sellerLayout;
        try {
            sellerLayout = sellerLayoutLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sellerLayout;
    }
    private BorderPane getStockistLayout(){
        FXMLLoader stockistLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/stockist/stockistLayout.fxml"));
        BorderPane stockistLayout;
        try {
            stockistLayout = stockistLayoutLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stockistLayout;
    }
}
