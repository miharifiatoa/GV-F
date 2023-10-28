package com.sales_management_javafx.controller.inventory;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.MenuIcon;
import com.sales_management_javafx.composent.MenuGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.InventoryEntity;
import org.sales_management.service.InventoryService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryLayoutController implements Initializable {
    @FXML
    private BorderPane inventoryLayoutBorderpane;
    private final InventoryService inventoryService;
    private final MenuGridPane menuGridPane;
    private final MenuIcon menuIcon;

    public InventoryLayoutController() {
        this.menuIcon = new MenuIcon();
        this.menuGridPane = new MenuGridPane();
        this.inventoryService = new InventoryService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putProductBoxLayout();
        if (this.inventoryService.getAll().isEmpty() || this.inventoryService.getAll().size()==0){
            InventoryEntity inventory = new InventoryEntity();
            inventory.setCode(100);
            inventoryService.create(inventory);
        }
    }
    private void putProductBoxLayout(){
        FXMLLoader productBoxLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productBoxLayout.fxml"));
        try {
            StackPane productBoxLayout = productBoxLayoutLoader.load();
            this.inventoryLayoutBorderpane.setCenter(productBoxLayout);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
