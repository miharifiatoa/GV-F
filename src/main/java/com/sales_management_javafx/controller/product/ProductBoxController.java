package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.classes.ProductFile;
import com.sales_management_javafx.composent.ProductGridPane;
import com.sales_management_javafx.composent.ProductShareGridPane;
import com.sales_management_javafx.controller.inventory.ShareProductBoxController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.ProductService;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class ProductBoxController implements Initializable {
    @FXML
    private StackPane productBoxStackpane;
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
    private Label productQuantityShareLabel;
    @FXML
    private Label productQuantityRetireLabel;
    @FXML
    private Label deleteLabel;
    @FXML
    private Button shareProductButton;
    @FXML
    private ScrollPane productScrollpane;
    @FXML
    private VBox productBox;
    @FXML
    private VBox confirmDeleteBox;
    @FXML
    private VBox addProductBox;
    @FXML
    private VBox shareProductBox;
    @FXML
    private Button deleteButton;
    @FXML
    private Button confirmDeleteButton;
    @FXML
    private Button editProductButton;
    @FXML
    private Button addProductButton;
    @FXML
    private Button addProductInShareListButton;
    @FXML
    private TextField quantityAddedTextfield;
    @FXML
    TextField quantitySharedTextfield;
    @FXML
    private Button confirmAddProductButton;
    @FXML
    private Button exitAddProductButton;
    @FXML
    private Button cancelDeleteButton;
    @FXML
    private VBox retireProductBox;
    @FXML
    private Button exitRetireProductButton;
    @FXML
    private Button confirmRetireProductButton;
    @FXML
    private TextField quantityRetireTextfield;
    @FXML
    private Button retireProductButton;
    @FXML
    private Button exitShareProductButton;
    @FXML
    private ImageView DeleteIcon;
    @FXML
    private ImageView EditIcon;
    @FXML
    private ImageView ShareIcon;
    private final ProductService productService;
    private final ArticleService articleService;
    private final ProductGridPane productGridPane;

    public ProductBoxController() {
        this.articleService = new ArticleService();
        this.productService = new ProductService();
        this.productGridPane = new ProductGridPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.productBox.setVisible(true);
        this.confirmDeleteBox.setVisible(false);
        this.addProductBox.setVisible(false);
        this.retireProductBox.setVisible(false);
        this.shareProductBox.setVisible(false);
        this.onDeleteProduct();
        this.onExitDeleteProduct();
        this.onEditProduct();
        this.onAddProduct();
        this.onExitAddProduct();
        this.onRetireProduct();
        this.onExitRetireProduct();
        this.onShareProduct();
        this.onExitShareProduct();
        this.formValidation();
        NumberTextField.requireIntegerOnly(quantityAddedTextfield,1000);
        this.putIcons();
    }
    public void initializeProductData(ProductEntity product){
        if (product.getQuantity()<=0){
            retireProductButton.setDisable(true);
        }
        this.nameLabel.setText(product.getName());
        Double price = product.getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        this.priceLabel.setText(decimalFormat.format(price)+ " Ar");
        this.sizeLabel.setText(String.valueOf(product.getSizes()));
        this.quantityLabel.setText(product.getQuantity() + " produit(s)");
        this.qualityLabel.setText(product.getQuality());
        this.brandLabel.setText(product.getBrand());
        this.referenceLabel.setText(product.getReference());
        this.colorLabel.setText(product.getColor());
        this.deleteLabel.setText("Voulez vous vraiment supprimer " + product.getName() + " dans la liste du produit?");
        this.productQuantityRetireLabel.setText("max : " + product.getQuantity());
        this.productQuantityShareLabel.setText("max : " + product.getQuantity());
        this.onConfirmDeleteProduct(product.getId());
        this.onConfirmAddProduct(product);
        this.onConfirmRetireProduct(product);
        this.onAddProductInShareList(product);
        NumberTextField.requireIntegerOnly(quantityRetireTextfield,product.getQuantity());
        NumberTextField.requireIntegerOnly(quantitySharedTextfield,product.getQuantity());
    }
    private void onConfirmDeleteProduct(Long product_id){
        this.confirmDeleteButton.setOnAction(actionEvent -> {
            ProductEntity product = this.productService.deleteById(product_id);
            if (product!=null){
                this.refreshData(product);
            }
        });
    }
    private void onDeleteProduct(){
        this.deleteButton.setOnAction(event->{
            this.productBox.setVisible(false);
            this.confirmDeleteBox.setVisible(true);
        });
    }
    private void onExitDeleteProduct(){
        this.cancelDeleteButton.setOnAction(event->{
            this.productBox.setVisible(true);
            this.confirmDeleteBox.setVisible(false);
        });
    }
    private void onEditProduct(){
        editProductButton.setOnAction(actionEvent -> {
            VBox editProductForm = (VBox) this.productBox.getParent().lookup("#productEditFormBox");
            editProductForm.setVisible(true);
            this.productBox.setVisible(false);
        });
    }
    private void onAddProduct(){
        addProductButton.setOnAction(event->{
            this.productBox.setVisible(false);
            this.addProductBox.setVisible(true);
        });
    }
    private void onConfirmAddProduct(ProductEntity product){
        confirmAddProductButton.setOnAction(event->{
            if (!this.quantityAddedTextfield.getText().isEmpty() && product!=null){
                ProductEntity productResponse = this.productService.addProduct(Integer.parseInt(this.quantityAddedTextfield.getText()),product);
                if (productResponse!=null){
                    this.refreshData(product);
                }
            }

        });
    }
    private void onExitAddProduct(){
        exitAddProductButton.setOnAction(event->{
            this.productBox.setVisible(true);
            this.addProductBox.setVisible(false);
        });
    }
    private void onRetireProduct(){
        this.retireProductButton.setOnAction(event->{
            this.retireProductBox.setVisible(true);
            this.productBox.setVisible(false);
        });
    }
    private void onConfirmRetireProduct(ProductEntity product){
        this.confirmRetireProductButton.setOnAction(event->{
            if (!quantityRetireTextfield.getText().isEmpty() && product!=null){
                ProductEntity productResponse = this.productService.retireProduct(Integer.parseInt(this.quantityRetireTextfield.getText()),product);
                if (productResponse!=null){
                    this.refreshData(product);
                }
            }
        });
    }
    private void onExitRetireProduct(){
        exitRetireProductButton.setOnAction(event->{
            this.retireProductBox.setVisible(false);
            this.productBox.setVisible(true);
        });
    }
    private void onShareProduct(){
        this.shareProductButton.setOnAction(event->{
            this.shareProductBox.setVisible(true);
            this.productBox.setVisible(false);
        });
    }
    private void onAddProductInShareList(ProductEntity product){
        addProductInShareListButton.setOnAction(event->{
            product.setQuantity(Integer.parseInt(quantitySharedTextfield.getText()));
            Collection<ProductEntity> existingProducts = ProductFile.readProductsFromFile();
            existingProducts.add(product);
            ProductFile.writeProductsToFile(existingProducts);
            this.refreshData(product);
        });
    }
    private void onExitShareProduct(){
        this.exitShareProductButton.setOnAction(event->{
            this.shareProductBox.setVisible(false);
            this.productBox.setVisible(true);
        });
    }
    private void formValidation(){
        if (quantityAddedTextfield.getText().isEmpty()){
            confirmAddProductButton.setDisable(true);
        }
        if (quantityRetireTextfield.getText().isEmpty()){
            confirmRetireProductButton.setDisable(true);
        }
        if (quantitySharedTextfield.getText().isEmpty()){
            addProductInShareListButton.setDisable(true);
        }
        quantityAddedTextfield.textProperty().addListener((observableValue, s, t1) -> {
            confirmAddProductButton.setDisable(quantityAddedTextfield.getText().isEmpty());
        });
        quantityRetireTextfield.textProperty().addListener((observableValue, s, t1) -> {
            confirmRetireProductButton.setDisable(quantityRetireTextfield.getText().isEmpty());
        });
        quantitySharedTextfield.textProperty().addListener((observableValue, s, t1) -> {
            addProductInShareListButton.setDisable(quantitySharedTextfield.getText().isEmpty());
        });
    }
    public void putIcons(){
        DeleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
        EditIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/EditIcon.png"))));
        ShareIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ShareIcon.png"))));
    }
    public void refreshData(ProductEntity product){
        ScrollPane productLayoutScrollpane = (ScrollPane) this.productBoxStackpane.getParent().getParent().getParent().getParent();
        BorderPane borderPane = (BorderPane) productLayoutScrollpane.getParent();
        if (borderPane.getBottom()!=null){
            BorderPane shareProductLayoutBorderpane = (BorderPane) borderPane.getBottom();
            ScrollPane shareProductLayoutScrollpane = (ScrollPane) shareProductLayoutBorderpane.getCenter();
            GridPane productShareGridpane = new ProductShareGridPane().getGridPane(ProductFile.readProductsFromFile(),2);
            shareProductLayoutScrollpane.setContent(productShareGridpane);
        }
        try {
            Long article_id = Long.valueOf(productBoxStackpane.getParent().getId());
            Collection<ProductEntity> products = this.articleService.getById(article_id).getProducts();
            GridPane productGridPane = new ProductGridPane().getGridPane(products, 3,true);
            productGridPane.setId(String.valueOf(product.getArticle().getId()));
            productLayoutScrollpane.setContent(productGridPane);
        }
        catch (NumberFormatException e){
            Collection<ProductEntity> products = this.productService.getAll();
            GridPane productGridPane = new ProductGridPane().getGridPane(products,3,false);
            productLayoutScrollpane.setContent(productGridPane);
        }
    }
}
