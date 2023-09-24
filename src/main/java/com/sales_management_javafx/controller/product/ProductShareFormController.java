package com.sales_management_javafx.controller.product;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.sales_management.entity.ShopEntity;
import org.sales_management.service.ShopService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductShareFormController implements Initializable {
    private ComboBox<ShopEntity> selectShopCombobox;
    private Button exitShareProductButton;
    private Button confirmShareProductButton;
    private TextField quantitySharedTextfield;
    private final ShopService shopService;

    public ProductShareFormController() {
        this.shopService = new ShopService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectShopCombobox.setItems(FXCollections.observableList((List<ShopEntity>) shopService.getAll()));
    }
    private void onExitShareProduct(){
        exitShareProductButton.setOnAction(event->{

        });
    }
}
