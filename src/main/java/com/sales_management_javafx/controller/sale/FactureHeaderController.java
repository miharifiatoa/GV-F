package com.sales_management_javafx.controller.sale;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.sales_management.entity.SaleEntity;
import org.sales_management.service.ShopService;

import java.net.URL;
import java.util.ResourceBundle;

public class FactureHeaderController implements Initializable {
    @FXML private Label shopNameLabel;
    @FXML private Label shopAdressLabel;
    @FXML private Label shopContactLabel;
    @FXML private Label clientName;
    private final ShopService shopService;

    public FactureHeaderController() {
        this.shopService = new ShopService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(SaleEntity sale){
        shopNameLabel.setText(shopService.getById(1L).getName());
        shopAdressLabel.setText(shopService.getById(1L).getAddress());
        shopContactLabel.setText(String.valueOf(shopService.getById(1L).getContact()));
        clientName.setText(sale.getClient().getName());
    }
}
