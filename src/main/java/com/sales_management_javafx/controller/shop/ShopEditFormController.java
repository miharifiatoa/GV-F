package com.sales_management_javafx.controller.shop;

import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.admin.AdminShopGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ShopEntity;
import org.sales_management.service.ShopService;

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
        shopContactTextfield.setText(shop.getContact());
        shopEmailTextfield.setText(shop.getEmail());
        } 
        
        formValidation();
        onUpdateShop(shop);
        NumberTextField.requireNumber(shopContactTextfield);
    }
    
    private void formValidation(){
        if (this.shopNameTextfield.getText().isEmpty() 
                || this.shopContactTextfield.getText().isEmpty()){
            editShopButton.setDisable(true);
        }
        
        shopNameTextfield.textProperty().addListener((observableValue, s, t1) -> editShopButton.setDisable(
                this.shopNameTextfield.getText().isEmpty()
                || this.shopContactTextfield.getText().isEmpty()));
        shopContactTextfield.textProperty().addListener((observableValue, s, t1) -> editShopButton.setDisable(
                this.shopNameTextfield.getText().isEmpty()
                || this.shopContactTextfield.getText().isEmpty()));
    }
    
    private void onUpdateShop(ShopEntity shop){
        editShopButton.setOnAction(actionEvent -> {
            if(shopNameTextfield.getText()!=null) shop.setName(shopNameTextfield.getText());
            if(shopAddressTextfield.getText()!=null) shop.setAddress(shopAddressTextfield.getText());
            if(shopContactTextfield.getText()!=null) shop.setContact(shopContactTextfield.getText());
            if(shopEmailTextfield.getText()!=null) shop.setEmail(shopEmailTextfield.getText());
           
            ShopEntity newShop = this.shopService.update(shop);
           if(newShop!=null){
            ScrollPane shopLayoutScrollpane = (ScrollPane) shopEditFormVBox.getParent().getParent().getParent();
            GridPane shopGridPane = new AdminShopGridPane().getGridPane(new ShopService().getAll(),4);
            shopLayoutScrollpane.setContent(shopGridPane);
            }
        });
    }
    
}
