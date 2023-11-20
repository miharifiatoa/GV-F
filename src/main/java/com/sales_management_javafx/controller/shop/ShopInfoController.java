package com.sales_management_javafx.controller.shop;

import com.sales_management_javafx.classes.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.sales_management.entity.ShareEntity;
import org.sales_management.entity.ShopEntity;
import org.sales_management.service.ShareService;
import org.sales_management.service.ShopService;

import java.net.URL;
import java.util.ResourceBundle;

public class ShopInfoController implements Initializable {
    @FXML private Label senderLabel;
    @FXML private Label dateLabel;
    @FXML private Label receiverLabel;
    private final ShopEntity shop;

    public ShopInfoController() {
        this.shop = new ShopService().getById(1L);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ShareEntity share){
        senderLabel.setText(shop.getName());
        receiverLabel.setText(share.getShop().getName());
        dateLabel.setText(DateTimeFormatter.format(share.getShareDate()));
    }
}
