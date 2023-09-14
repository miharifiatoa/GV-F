package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.classes.NumberField;
import com.sales_management_javafx.composent.ProductGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ProductService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductEditFormController implements Initializable {
    @FXML
    private TextField productNameTextfield;
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
    private Button confirmEditProductButton;
    @FXML
    private Button cancelEditProductButton;
    @FXML
    private ScrollPane productFormScrollpane;
    @FXML
    private VBox productEditFormBox;
    @FXML
    ProductGridPane productGridPane =new ProductGridPane();
    private final ProductService productService;

    public ProductEditFormController() {
        this.productService = new ProductService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.productFormScrollpane.setMinHeight(130);
        this.productFormScrollpane.setMaxHeight(130);
        this.formValidation();
        this.onExitEditProduct();
        NumberField.requireDecimal(this.productPriceTextfield);
    }
    private void formValidation(){
        if (this.productNameTextfield.getText().isEmpty() || this.productPriceTextfield.getText().isEmpty()){
            this.confirmEditProductButton.setDisable(true);
        }
        this.productNameTextfield.textProperty().addListener(((observableValue, s, t1) -> confirmEditProductButton.setDisable(this.productNameTextfield.getText().isEmpty() || this.productPriceTextfield.getText().isEmpty())));
        this.productPriceTextfield.textProperty().addListener(((observableValue, s, t1) -> confirmEditProductButton.setDisable(this.productNameTextfield.getText().isEmpty() || this.productPriceTextfield.getText().isEmpty())));
    }
    public void initializeForm(ProductEntity product){
        this.productNameTextfield.setText(product.getName());
        this.productPriceTextfield.setText(String.valueOf(product.getPrice()));
        this.productSizeTextfield.setText(product.getSizes());
        this.productBrandTextfield.setText(product.getBrand());
        this.productReferenceTextfield.setText(product.getReference());
        this.productQualityTextfield.setText(product.getQuality());
        this.productColorTextfield.setText(product.getColor());
    }
    public void onConfirmEditProduct(Long id){
        confirmEditProductButton.setOnAction(actionEvent -> {
            ProductEntity product = this.productService.getById(id);
            product.setName(this.productNameTextfield.getText());
            product.setSizes(this.productSizeTextfield.getText());
            product.setPrice(Double.valueOf(this.productPriceTextfield.getText()));
            product.setBrand(this.productBrandTextfield.getText());
            product.setQuality(this.productQualityTextfield.getText());
            product.setReference(this.productReferenceTextfield.getText());
            product.setColor(this.productColorTextfield.getText());
            if (this.productService.update(product)!=null){
                ScrollPane productBoxLayoutScrollpane = (ScrollPane) productEditFormBox.getParent().getParent().getParent().getParent().getParent();
                productBoxLayoutScrollpane.setContent(this.productGridPane.getGridPane(this.productService.getAll(),4));
            }
        });
    }
    public void onExitEditProduct(){
        cancelEditProductButton.setOnAction(actionEvent -> {
            VBox productBox = (VBox) productEditFormBox.getParent().lookup("#productBox");
            productEditFormBox.setVisible(false);
            productBox.setVisible(true);
        });
    }
}
