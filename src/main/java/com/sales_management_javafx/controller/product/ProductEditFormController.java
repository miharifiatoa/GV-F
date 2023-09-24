package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.ProductGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.ProductService;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Collection;
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
    private final ArticleService articleService;

    public ProductEditFormController() {
        this.articleService = new ArticleService();
        this.productService = new ProductService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.formValidation();
        this.onExitEditProduct();
        NumberTextField.requireDouble(this.productPriceTextfield);
    }
    private void formValidation(){
        if (this.productNameTextfield.getText().isEmpty() || this.productPriceTextfield.getText().isEmpty()){
            this.confirmEditProductButton.setDisable(true);
        }
        this.productNameTextfield.textProperty().addListener(((observableValue, s, t1) -> confirmEditProductButton.setDisable(this.productNameTextfield.getText().isEmpty() || this.productPriceTextfield.getText().isEmpty())));
        this.productPriceTextfield.textProperty().addListener(((observableValue, s, t1) -> confirmEditProductButton.setDisable(this.productNameTextfield.getText().isEmpty() || this.productPriceTextfield.getText().isEmpty())));
    }
    public void initializeForm(ProductEntity product){
        Double price = product.getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
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
            ProductEntity productResponse = this.productService.update(product);
            if (productResponse!=null){
                ScrollPane productBoxLayoutScrollpane = (ScrollPane) productEditFormBox.getParent().getParent().getParent().getParent().getParent();
                try {
                    Long article_id = Long.valueOf(productEditFormBox.getParent().getParent().getId());
                    Collection<ProductEntity> products = this.articleService.getById(article_id).getProducts();
                    GridPane productGridPane = new ProductGridPane().getGridPane(products, 3,true);
                    productGridPane.setId(String.valueOf(article_id));
                    productBoxLayoutScrollpane.setContent(productGridPane);
                }
                catch (NumberFormatException e){
                    Collection<ProductEntity> products = this.productService.getAll();
                    GridPane productGridPane = new ProductGridPane().getGridPane(products, 3,false);
                    productBoxLayoutScrollpane.setContent(productGridPane);
                }
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
