package com.sales_management_javafx.controller;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.sales_management.entity.*;
import org.sales_management.service.AccountService;
import org.sales_management.service.InventoryService;
import org.sales_management.service.ShopService;
import org.sales_management.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SalesController implements Initializable {
    @FXML
    private BorderPane salesManagementBorderpane;
    private final InventoryService inventoryService;
    private final AccountService accountService;
    private final UserService userService;
    private final ShopService shopService;

    public SalesController() {
        this.shopService = new ShopService();
        this.accountService = new AccountService();
        this.userService = new UserService();
        this.inventoryService = new InventoryService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/login/login.fxml"));
        try {
            GridPane loginGridpane = fxmlLoader.load();
            this.salesManagementBorderpane.setCenter(loginGridpane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (this.inventoryService.getAll().isEmpty() || this.inventoryService.getAll().size()==0){
            InventoryEntity inventory = new InventoryEntity();
            inventory.setCode(100);
            inventoryService.create(inventory);
        }
        if (this.accountService.getAll().isEmpty() || this.accountService.getAll().size()==0){
            AccountEntity account = new AccountEntity();
            UserEntity user = new UserEntity();
            user.setRole("ADMIN");
            userService.create(user);
            account.setPassword(hashedPassword("admin"));
            account.setUsername("admin");
            account.setUser(user);
            accountService.create(account);
        }
        if (shopService.getAll().isEmpty() || shopService.getAll().size() == 0){
            ShopEntity shop = new ShopEntity();
            shop.setContact("0340236845");
            shop.setAddress("Ampasambazaha");
            shop.setName("Boutique One");
            shopService.create(shop);
        }
    }
    public String hashedPassword(String password){
        return DigestUtils.sha256Hex(password);
    }
}