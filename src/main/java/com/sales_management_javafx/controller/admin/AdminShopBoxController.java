package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.sales_management.entity.ShopEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminShopBoxController implements Initializable {
    @FXML private Label shopNameLabel;
    @FXML private Label shopAddresslabel;
    @FXML private Label shopcontactLabel;
    @FXML private Label shopEmailLabel;
    @FXML private ImageView deleteIcon;
    @FXML private ImageView editIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
    }
    public void initialize(ShopEntity shop){
        shopNameLabel.setText(shop.getName());
        shopAddresslabel.setText(shop.getAddress());
        shopcontactLabel.setText(String.valueOf(shop.getContact()));
        shopEmailLabel.setText(shop.getEmail());
    }
    private void putIcons(){
        this.editIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/EditIcon.png"))));
        this.deleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
    }
}
