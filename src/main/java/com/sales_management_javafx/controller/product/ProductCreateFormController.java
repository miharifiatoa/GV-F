package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.ProductGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.InventoryEntity;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.InventoryService;
import org.sales_management.service.ProductService;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class ProductCreateFormController implements Initializable {
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
    private Button confirmCreateProductButton;
    @FXML
    private Button cancelCreateProductButton;
    @FXML
    private ScrollPane productFormScrollpane;
    @FXML
    private Button createProductButton;
    @FXML
    private StackPane productCreateFormStackPane;
    @FXML
    private VBox productFormBox;
    @FXML
    private BorderPane productCreatePane;
    @FXML
    private Label titleLabel;
    private final ArticleService articleService;
    private final InventoryService inventoryService;
    private final ProductService productService;
//    private final ProductGridPane productGridPane;

    public ProductCreateFormController() {
        this.articleService = new ArticleService();
        this.inventoryService = new InventoryService();
        this.productService = new ProductService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.productFormBox.setVisible(false);
        this.productCreatePane.setVisible(true);
        this.formValidation();
        this.onCreateProduct();
        this.onCancelCreateProduct();
//        this.onConfirmCreateProduct();
        this.onClickOutOfProductStackPane();
        NumberTextField.requireDouble(this.productPriceTextfield);
    }
    private void formValidation(){
        if (this.productNameTextfield.getText().isEmpty() || this.productPriceTextfield.getText().isEmpty()){
            this.confirmCreateProductButton.setDisable(true);
        }
        this.productNameTextfield.textProperty().addListener(((observableValue, s, t1) -> confirmCreateProductButton.setDisable(this.productNameTextfield.getText().isEmpty() || this.productPriceTextfield.getText().isEmpty())));
        this.productPriceTextfield.textProperty().addListener(((observableValue, s, t1) -> confirmCreateProductButton.setDisable(this.productNameTextfield.getText().isEmpty() || this.productPriceTextfield.getText().isEmpty())));
    }
    private void onCreateProduct(){
        createProductButton.setOnAction(actionEvent -> {
            productFormBox.setVisible(true);
            productCreatePane.setVisible(false);
            actionEvent.consume();
        });
    }
    private void onCancelCreateProduct(){
        cancelCreateProductButton.setOnAction(actionEvent -> {
            productFormBox.setVisible(false);
            productCreatePane.setVisible(true);
        });
    }
//    private void onConfirmCreateProduct(){
//        confirmCreateProductButton.setOnAction(actionEvent -> {
//            ProductEntity product = this.createProduct();
//            if (product!=null){
//                ScrollPane productBoxLayout = (ScrollPane) productCreateFormStackPane.getParent().getParent().getParent().getParent();
//                GridPane productGridPane = new ProductGridPane().getGridPane(this.articleService.getById(Long.valueOf(productCreateFormStackPane.getParent().getId())).getProducts(), 3,true);
//                productGridPane.setId(String.valueOf(product.getArticle().getId()));
//                productBoxLayout.setContent(productGridPane);
//                productFormBox.setVisible(false);
//                productCreatePane.setVisible(true);
//            }
//        });
//    }
//    public ProductEntity createProduct(){
//        ProductEntity product = new ProductEntity();
//        Long article_id = Long.valueOf(productCreateFormStackPane.getParent().getId());
//        ArticleEntity article = this.articleService.getById(article_id);
//        InventoryEntity inventory = this.inventoryService.getById(1L);
//        product.setName(this.productNameTextfield.getText());
//        product.setPrice(Double.valueOf(this.productPriceTextfield.getText()));
//        product.setSizes(this.productSizeTextfield.getText());
//        product.setQuantity(0);
//        product.setQuality(this.productQualityTextfield.getText());
//        product.setBrand(this.productBrandTextfield.getText());
//        product.setReference(this.productReferenceTextfield.getText());
//        product.setColor(this.productColorTextfield.getText());
//        product.setArticle(article);
//        product.setInventory(inventory);
//        if (article!=null && inventory!=null && !this.productNameTextfield.getText().isEmpty() && !this.productPriceTextfield.getText().isEmpty()){
//            this.productService.create(product);
//        }
//        return product;
//    }
    public void onClickOutOfProductStackPane(){

    }
}
