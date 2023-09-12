package com.sales_management_javafx.controller.product;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ProductEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductBoxController implements Initializable {
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
    private Label quantityLabel;
    @FXML
    private Label qualityLabel;
    @FXML
    private Label arrivalLabel;
    @FXML
    private Button shareProductButton;
    @FXML
    private ScrollPane productScrollpane;
    @FXML
    private VBox productBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productScrollpane.setMinHeight(130);
        productScrollpane.setMaxHeight(130);
    }
    public void initializeProductData(ProductEntity product){
        this.nameLabel.setText(product.getName());
        this.priceLabel.setText(String.valueOf(product.getPrice()));
        this.brandLabel.setText(product.getBrand());
        this.referenceLabel.setText(product.getReference());
        this.colorLabel.setText(product.getColor());
        this.quantityLabel.setText(String.valueOf(product.getQuantity()));
        this.qualityLabel.setText(product.getQuality());
        this.arrivalLabel.setText(String.valueOf(product.getArrival()));
    }
}
