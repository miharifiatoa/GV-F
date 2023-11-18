package com.sales_management_javafx.controller.shop;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.ShopGridPane;
import java.io.IOException;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ShopEntity;
import org.sales_management.service.ShopService;

public class ConfirmDeleteShopController implements Initializable {
    @FXML VBox confirm_delete_shop_form;
    @FXML Label messLabel;
    @FXML Button confirmer;
    @FXML Button annuler;
    private final ShopService shopService;
    public ConfirmDeleteShopController() {
        this.shopService = new ShopService();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ShopEntity shop){
        if(shop!=null){
            messLabel.setText("Attention, le shop : "+shop.getName()+" va etre supprimer !");
        }
        delete(shop);
        closeForm();
    }
    public void delete(ShopEntity shop){
        confirmer.setOnAction(actionEvent -> {
        if(shop!=null){
                
        ShopEntity dropedShop = this.shopService.deleteById(shop.getId());
        if(dropedShop!=null){
            BorderPane dashboardLayout = (BorderPane) this.confirm_delete_shop_form.getParent();
            ScrollPane dashboardLayoutScrollpane = (ScrollPane) dashboardLayout.lookup("#dashboardLayoutScrollpane");
            GridPane shopGridPane = new ShopGridPane().getGridPane(new ShopService().getAll(),4);
            dashboardLayoutScrollpane.setContent(shopGridPane);
            dashboardLayout.setBottom(this.getDashboardToolbar());
                }
            }
        });
    }
    
    public void closeForm(){
        annuler.setOnAction(actionEvent -> {
            BorderPane dashboardLayout = (BorderPane) confirm_delete_shop_form.getParent().getParent();
            dashboardLayout.setBottom(getDashboardToolbar());
        });
    }
    
    private BorderPane getDashboardToolbar(){
    FXMLLoader shopLayotLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shop2Layout.fxml"));
        BorderPane shopLayout;
        try {
            shopLayout = shopLayotLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return shopLayout;
    }
}