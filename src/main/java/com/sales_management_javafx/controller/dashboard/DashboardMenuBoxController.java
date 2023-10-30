package com.sales_management_javafx.controller.dashboard;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardMenuBoxController implements Initializable {
    @FXML
    private StackPane dashboardMenuBox;
    @FXML
    private GridPane dashboardMenu;
    @FXML
    private ImageView menuIcon;
    @FXML
    private Label menuLabel;
    private BorderPane account;
    private BorderPane shop;
    private BorderPane article;
    private BorderPane product;
    private BorderPane sale;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(String menu, String image_name){
        this.menuIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/"+ image_name))));
        this.menuLabel.setText(menu);
        this.dashboardMenu.setId(menu);
        this.initialize();
        this.onShowContent();
    }
    public void initialize(){
        FXMLLoader accountLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountLayout.fxml"));
        FXMLLoader shopLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shopLayout.fxml"));
        FXMLLoader articleLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_category/productCategoryLayout.fxml"));
        try {
            this.account = accountLoader.load();
            this.shop = shopLoader.load();
            this.article = articleLoader.load();
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }
    public void onShowContent(){
        dashboardMenu.setOnMouseClicked(this::handleClickMenuItems);
    }
    public void handleClickMenuItems(MouseEvent event){
        BorderPane dashboard = (BorderPane) dashboardMenuBox.getParent().getParent();
        switch (dashboardMenu.getId()) {
            case "ACCOUNT" -> dashboard.setCenter(this.account);
            case "SHOP" -> dashboard.setCenter(this.shop);
            case "ARTICLE" -> dashboard.setCenter(this.article);
            case "INVENTORY" -> {
                dashboard.setCenter(this.getInventoryLayout());
                dashboard.setLeft(null);
            }
        }
        event.consume();
    }
    public BorderPane getInventoryLayout(){
        FXMLLoader loadInventory = new FXMLLoader(SalesApplication.class.getResource("fxml/inventory/inventoryLayout.fxml"));
        BorderPane inventoryLayout;
        try {
            inventoryLayout = loadInventory.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return inventoryLayout;
    }
}
