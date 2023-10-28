package com.sales_management_javafx.controller.seller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.sales_management.entity.ArticleEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerPriceVariationBoxController implements Initializable {
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
    public void initialize(ArticleEntity priceVariation){
        if (priceVariation.getQuantity() == 0){
            saleProductButton.setDisable(true);
        }
        priceLabel.setText(priceVariation.getPrice() +" Ar");
        colorLabel.setText(priceVariation.getColor());
        sizeLabel.setText(priceVariation.getSize());
        quantityLabel.setText(String.valueOf(priceVariation.getQuantity()));
    }
}
