package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.composent.ProductGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.InventoryEntity;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.InventoryService;
import org.sales_management.service.ProductService;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ProductFormController implements Initializable {
    @FXML
    private TextField productNameTextfield;
    @FXML
    private TextField productPriceTextfield;
    @FXML
    private TextField productQuantityTextfield;
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
    private StackPane productFormStackPane;
    @FXML
    private VBox productFormBox;
    @FXML
    private BorderPane productCreatePane;
    private final ArticleService articleService;
    private final InventoryService inventoryService;
    private final ProductService productService;
    private final ProductGridPane productGridPane;

    public ProductFormController() {
        this.articleService = new ArticleService();
        this.inventoryService = new InventoryService();
        this.productService = new ProductService();
        this.productGridPane = new ProductGridPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productFormScrollpane.setMinHeight(130);
        productFormScrollpane.setMaxHeight(130);
        this.productFormBox.setVisible(false);
        this.productCreatePane.setVisible(true);
        this.formValidation();
        this.onCreateProduct();
        this.onCancelCreateProduct();
        this.onConfirmCreateProduct();
    }
    private void formValidation(){
        if (this.productNameTextfield.getText().isEmpty() || this.productPriceTextfield.getText().isEmpty() && this.productQuantityTextfield.getText().isEmpty()){
            this.confirmCreateProductButton.setDisable(true);
        }
        this.productNameTextfield.textProperty().addListener(((observableValue, s, t1) -> confirmCreateProductButton.setDisable(this.productNameTextfield.getText().isEmpty() || this.productPriceTextfield.getText().isEmpty() || this.productQuantityTextfield.getText().isEmpty())));
        this.productPriceTextfield.textProperty().addListener(((observableValue, s, t1) -> confirmCreateProductButton.setDisable(this.productNameTextfield.getText().isEmpty() || this.productPriceTextfield.getText().isEmpty() || this.productQuantityTextfield.getText().isEmpty())));
        this.productQuantityTextfield.textProperty().addListener(((observableValue, s, t1) -> confirmCreateProductButton.setDisable(this.productNameTextfield.getText().isEmpty() || this.productPriceTextfield.getText().isEmpty() || this.productQuantityTextfield.getText().isEmpty())));
    }
    private void onCreateProduct(){
        createProductButton.setOnAction(actionEvent -> {
            productFormBox.setVisible(true);
            productCreatePane.setVisible(false);
        });
    }
    private void onCancelCreateProduct(){
        cancelCreateProductButton.setOnAction(actionEvent -> {
            productFormBox.setVisible(false);
            productCreatePane.setVisible(true);
        });
    }
    private void onConfirmCreateProduct(){
        confirmCreateProductButton.setOnAction(actionEvent -> {
            if (this.createProduct()!=null){
                ScrollPane productBoxLayout = (ScrollPane) productFormStackPane.getParent().getParent().getParent().getParent();
                productBoxLayout.setContent(this.productGridPane.getGridPane(this.productService.getAll(), 4));
                productFormBox.setVisible(false);
                productCreatePane.setVisible(true);
            }
        });
    }
    public ProductEntity createProduct(){
        ProductEntity product = new ProductEntity();
        ArticleEntity article = this.articleService.getById(1L);
        InventoryEntity inventory = this.inventoryService.getById(1L);
        product.setName(this.productNameTextfield.getText());
        product.setPrice(Double.valueOf(this.productPriceTextfield.getText()));
        product.setQuantity(Integer.parseInt(this.productQuantityTextfield.getText()));
        product.setQuality(this.productQualityTextfield.getText());
        product.setBrand(this.productBrandTextfield.getText());
        product.setReference(this.productReferenceTextfield.getText());
        product.setColor(this.productColorTextfield.getText());
        product.setArrival(LocalDateTime.now());
        if (article != null){
            product.setArticle(article);
        }
        if (inventory != null){
            product.setInventory(inventory);
        }
        if (!this.productNameTextfield.getText().isEmpty() && !this.productPriceTextfield.getText().isEmpty() && !this.productQuantityTextfield.getText().isEmpty()){
            this.productService.create(product);
        }
        return product;
    }
}
