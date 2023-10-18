package com.sales_management_javafx.controller.product_variation;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.NumberTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.PriceVariationEntity;
import org.sales_management.service.PriceVariationService;
import org.sales_management.service.ProductService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PriceVariationCreateController implements Initializable {
    @FXML
    private TextField productPriceTextfield;
    @FXML
    private TextField productSizeTextfield;
    @FXML
    private TextField productQualityTextfield;
    @FXML
    private TextField productBrandTextfield;
    @FXML
    private TextField productReferenceTextfield;
    @FXML
    private TextField productColorTextfield;
    @FXML
    private Button confirmCreateProductButton;
    @FXML
    private Button cancelCreateProductButton;
    @FXML
    private ScrollPane priceCreateScrollpane;
    @FXML
    private StackPane priceVariationCreateStackPane;
    @FXML
    private VBox priceVariationCreateVBox;
    @FXML
    private Label titleLabel;
    private final ProductService productService;
    private final PriceVariationService priceVariationService;

    public PriceVariationCreateController() {
        this.productService = new ProductService();
        this.priceVariationService = new PriceVariationService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.formValidation();
            this.onCancelCreateProduct();
//        this.onConfirmCreateProduct();
        NumberTextField.requireDouble(this.productPriceTextfield);
    }
    private void formValidation(){
        if (this.productPriceTextfield.getText().isEmpty()){
            this.confirmCreateProductButton.setDisable(true);
        }
        this.productPriceTextfield.textProperty().addListener(((observableValue, s, t1) -> confirmCreateProductButton.setDisable(this.productPriceTextfield.getText().isEmpty())));
    }
    private void onCancelCreateProduct(){
        cancelCreateProductButton.setOnAction(actionEvent -> {
            BorderPane productBoxLayoutBorderpane = (BorderPane) priceVariationCreateStackPane.getParent();
            productBoxLayoutBorderpane.setBottom(this.getToolbar());
        });
    }
    private void onConfirmCreatePrice(){
        confirmCreateProductButton.setOnAction(actionEvent -> {
            PriceVariationEntity priceVariation = this.createPriceVariation();
            if (priceVariation!=null){
                ScrollPane productBoxLayout = (ScrollPane) priceVariationCreateStackPane.getParent().getParent().getParent().getParent();

            }
        });
    }
    public PriceVariationEntity createPriceVariation(){
        PriceVariationEntity priceVariation = new PriceVariationEntity();
        priceVariation.setPrice(Double.valueOf(this.productPriceTextfield.getText()));
        priceVariation.setSize(this.productSizeTextfield.getText());
        priceVariation.setQuantity(0);
        priceVariation.setQuality(this.productQualityTextfield.getText());
        priceVariation.setBrand(this.productBrandTextfield.getText());
        priceVariation.setReference(this.productReferenceTextfield.getText());
        priceVariation.setColor(this.productColorTextfield.getText());
        if (!this.productPriceTextfield.getText().isEmpty()){
            this.priceVariationService.create(priceVariation);
        }
        return priceVariation;
    }
    private StackPane getToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productToolbar.fxml"));
        StackPane toolbar;
        try {
            toolbar = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toolbar;
    }
}
