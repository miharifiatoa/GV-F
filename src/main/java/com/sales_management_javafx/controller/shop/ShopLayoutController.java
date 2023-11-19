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

public class ShopLayoutController implements Initializable {
    @FXML private BorderPane shopLayout;
    @FXML private Button exit;
    @FXML private Label newShopLabel;
    @FXML private Label shopLabel;
    @FXML private ImageView ShopIcon;
    @FXML private ScrollPane shopLayoutScrollpane;
    private final ShopService shopService;
    public ShopLayoutController() {
        this.shopService = new ShopService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        this.exit.setOnAction(event->this.setExit());
        this.setShops();
        this.setNewShopLabel();
        this.setShopLabel();
    }
    
    public void setShops(){
        GridPane shopGridPane = new AdminShopGridPane().getGridPane(shopService.getAll(),4);
        shopLayoutScrollpane.setContent(shopGridPane);
    }
    
    public void setNewShopLabel(){
        newShopLabel.setOnMouseClicked(event->{
            shopLayoutScrollpane.setContent(this.getShopForm());
        });
    }
    public void setShopLabel(){
        shopLabel.setOnMouseClicked(event->{
            this.setShops();
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
