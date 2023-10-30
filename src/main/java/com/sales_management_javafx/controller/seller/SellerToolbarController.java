package com.sales_management_javafx.controller.seller;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SellerToolbarController implements Initializable {
    @FXML private GridPane pannierToolbar;
    @FXML private Button  pannier;
    @FXML private Button  sale;
    @FXML private ImageView pannierIcon;
    @FXML private ImageView  saleIcon;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setPannier();
        this.setSale();
        this.putIcons();
    }
    private void setPannier(){
        pannier.setOnAction(event->{
            BorderPane sellerLayout = (BorderPane) pannierToolbar.getParent();
            sellerLayout.setBottom(getPannierLayout());
        });
    }
    private void setSale(){
        sale.setOnAction(event->{
            BorderPane sellerLayout = (BorderPane) pannierToolbar.getParent();
            sellerLayout.setBottom(getSaleLayout());
        });
    }
    private void putIcons(){
        this.pannierIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/PannierIcon.png"))));
        this.saleIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/SaleIcon.png"))));
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
    private BorderPane getSaleLayout(){
        FXMLLoader pannierLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/sale/saleLayout.fxml"));
        BorderPane pannierLayout;
        try {
            pannierLayout = pannierLayoutLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pannierLayout;
    }
}
