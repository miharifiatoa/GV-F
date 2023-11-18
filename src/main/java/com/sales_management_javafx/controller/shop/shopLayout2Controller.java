package com.sales_management_javafx.controller.shop;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.admin.AdminShopGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.service.ShopService;

public class shopLayout2Controller implements Initializable {
    @FXML private BorderPane shopLayout;
    @FXML private Button exit;
    @FXML private Button newShop;
    @FXML private ImageView ShopIcon;
    @FXML private ScrollPane shopLayoutScrollpane;
    private final ShopService shopService;
    public shopLayout2Controller() {
        this.shopService = new ShopService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        this.exit.setOnAction(event->this.setExit());
        onCreateShop();
        this.setShops();
    }
    
    public void setShops(){
        GridPane shopGridPane = new AdminShopGridPane().getGridPane(shopService.getAll(),3);
        shopLayoutScrollpane.setContent(shopGridPane);
    }
    
    public void onCreateShop(){
        newShop.setOnAction(event->{
            BorderPane dashboardLayout = (BorderPane) shopLayout.getParent();
            dashboardLayout.setBottom(getShopForm());
        });
    }
    
    public VBox getShopForm(){
        FXMLLoader shopFormLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shopForm.fxml"));
        VBox shopForm;
        try {
            shopForm = shopFormLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return shopForm;
    }
    
    private void setExit(){
        BorderPane borderPane = (BorderPane) this.shopLayout.getParent();
        FXMLLoader dashboardToolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/dashboard/dashboardToolbar.fxml"));
        StackPane dashboardToolbar;
        try {
            dashboardToolbar = dashboardToolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        borderPane.setBottom(dashboardToolbar);
    
    }
    
private void putIcons(){
        ShopIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/NewShopIcon.png"))));
    }
}
