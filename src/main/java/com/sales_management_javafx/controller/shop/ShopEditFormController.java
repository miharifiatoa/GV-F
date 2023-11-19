package com.sales_management_javafx.controller.shop;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.stockist.StockistShopGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ShopEntity;
import org.sales_management.service.ShopService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class ShopEditFormController implements Initializable {
    @FXML
    private VBox shopEditFormVBox;
    @FXML
    private TextField shopNameTextfield;
    @FXML
    private TextField shopAddressTextfield;
    @FXML
    private TextField shopContactTextfield;
    @FXML
    private TextField shopEmailTextfield;
    @FXML
    private Button editShopButton;
    private final ShopService shopService;

    public ShopEditFormController() {
        this.shopService = new ShopService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    
    public void initialize(ShopEntity shop){
        if(shop!=null){
        shopNameTextfield.setText(shop.getName());
        shopAddressTextfield.setText(shop.getAddress());
        shopContactTextfield.setText(shop.getContact().toString());
        shopEmailTextfield.setText(shop.getEmail());
        }
        
        this.formValidation();
        this.onUpdateShop(shop);
        NumberTextField.requireNumber(shopContactTextfield);
    }
    
    private void formValidation(){
        if (this.shopNameTextfield.getText().isEmpty() 
                || this.shopAddressTextfield.getText().isEmpty()
                || this.shopContactTextfield.getText().isEmpty()
                || this.shopEmailTextfield.getText().isEmpty()){
            editShopButton.setDisable(true);
        }
        shopNameTextfield.textProperty().addListener((observableValue, s, t1) -> editShopButton.setDisable(
                this.shopNameTextfield.getText().isEmpty()
                || this.shopAddressTextfield.getText().isEmpty()
                || this.shopContactTextfield.getText().isEmpty()
                || this.shopEmailTextfield.getText().isEmpty()));
        shopAddressTextfield.textProperty().addListener((observableValue, s, t1) -> editShopButton.setDisable(
                this.shopNameTextfield.getText().isEmpty()
                || this.shopAddressTextfield.getText().isEmpty()
                || this.shopContactTextfield.getText().isEmpty()
                || this.shopEmailTextfield.getText().isEmpty()));
        shopContactTextfield.textProperty().addListener((observableValue, s, t1) -> editShopButton.setDisable(
                this.shopNameTextfield.getText().isEmpty()
                || this.shopAddressTextfield.getText().isEmpty()
                || this.shopContactTextfield.getText().isEmpty()
                || this.shopEmailTextfield.getText().isEmpty()));
        shopEmailTextfield.textProperty().addListener((observableValue, s, t1) -> editShopButton.setDisable(
                this.shopNameTextfield.getText().isEmpty()
                || this.shopAddressTextfield.getText().isEmpty()
                || this.shopContactTextfield.getText().isEmpty()
                || this.shopEmailTextfield.getText().isEmpty()));
    }
    
    private void onUpdateShop(ShopEntity shop){
        editShopButton.setOnAction(actionEvent -> {
            if(!shopNameTextfield.getText().isEmpty()) shop.setName(shopNameTextfield.getText());
            if(!shopAddressTextfield.getText().isEmpty()) shop.setAddress(shopAddressTextfield.getText());
            if(!shopContactTextfield.getText().isEmpty()) shop.setContact(shopContactTextfield.getText());
            if(!shopEmailTextfield.getText().isEmpty()) shop.setEmail(shopEmailTextfield.getText());
           
            ShopEntity newShop = this.shopService.update(shop);
           if(newShop.getId()>0){
                BorderPane dashboardLayout = (BorderPane) this.shopEditFormVBox.getParent();
            ScrollPane dashboardLayoutScrollpane = (ScrollPane) dashboardLayout.lookup("#dashboardLayoutScrollpane");
            GridPane shopGridPane = new StockistShopGridPane().getGridPane(new ShopService().getAll(),4);
            dashboardLayoutScrollpane.setContent(shopGridPane);
            dashboardLayout.setBottom(this.getDashboardToolbar());
           }
            
        });
    }
    private BorderPane getDashboardToolbar(){
        FXMLLoader shopLayotLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shopLayout.fxml"));
        BorderPane shopLayout;
        try {
            shopLayout = shopLayotLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return shopLayout;
    }
}
