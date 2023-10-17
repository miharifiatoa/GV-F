package com.sales_management_javafx.controller.seller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.sales_management.entity.ProductEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerProductBoxController implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label brandLabel;
    @FXML
    private Label referenceLabel;
    @FXML
    private Label colorLabel;
    @FXML
    private Label sizeLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label qualityLabel;
    @FXML
    private Button saleProductButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
//    public void initialize(ProductEntity product){
//        if (product.getQuantity() == 0){
//            saleProductButton.setDisable(true);
//        }
//        nameLabel.setText(product.getName());
//        priceLabel.setText(String.valueOf(product.getPrice()));
//        brandLabel.setText(product.getBrand());
//        referenceLabel.setText(product.getReference());
//        colorLabel.setText(product.getColor());
//        qualityLabel.setText(product.getQuality());
//        sizeLabel.setText(product.getSizes());
//        quantityLabel.setText(String.valueOf(product.getQuantity()));
//    }
}
