package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.ProductGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ProductService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.SynchronousQueue;

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
    private Label arrivalLabel;
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
    private Button deleteButton;
    @FXML
    private Button confirmDeleteButton;
    @FXML
    private Button editProductButton;
    @FXML
    private Button addProductButton;
    @FXML
    private TextField quantityAddedTextfield;
    @FXML
    private Button confirmAddProductButton;
    @FXML
    private Button exitAddProductButton;
    @FXML
    private Button cancelDeleteButton;
    private final ProductService productService;
    private final ProductGridPane productGridPane;

    public ProductBoxController() {
        this.productService = new ProductService();
        this.productGridPane = new ProductGridPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productScrollpane.setMinHeight(130);
        productScrollpane.setMaxHeight(130);
        this.productBox.setVisible(true);
        this.confirmDeleteBox.setVisible(false);
        this.addProductBox.setVisible(false);
        this.quantityAddedTextfield.setPromptText("Nombres de produit");
        this.onDeleteProduct();
        this.onExitDeleteProduct();
        this.onEditProduct();
        this.onAddProduct();
    }
    public void initializeProductData(ProductEntity product){
        this.nameLabel.setText(product.getName());
        this.priceLabel.setText(String.valueOf(product.getPrice()));
        this.sizeLabel.setText(String.valueOf(product.getSizes()));
        this.quantityLabel.setText(String.valueOf(product.getQuantity()));
        this.qualityLabel.setText(product.getQuality());
        this.brandLabel.setText(product.getBrand());
        this.referenceLabel.setText(product.getReference());
        this.colorLabel.setText(product.getColor());
        this.deleteLabel.setText("Voulez vous vraiment supprimer " + product.getName() + " dans la liste du produit?");
    }
    public void onConfirmDeleteProduct(Long product_id){
        this.confirmDeleteButton.setOnAction(actionEvent -> {
            if (this.productService.deleteById(product_id)!=null){
                ScrollPane productLayoutScrollpane = (ScrollPane) this.productBoxStackpane.getParent().getParent().getParent().getParent();
                productLayoutScrollpane.setContent(this.productGridPane.getGridPane(this.productService.getAll(),4));
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
    public void onConfirmAddProduct(ProductEntity product){
        confirmAddProductButton.setOnAction(event->{
            if (!quantityAddedTextfield.getText().isEmpty()){
                if (this.productService.addProduct(Integer.parseInt(this.quantityAddedTextfield.getText()),product)!=null){
                    ScrollPane productLayoutScrollpane = (ScrollPane) this.productBoxStackpane.getParent().getParent().getParent().getParent();
                    productLayoutScrollpane.setContent(this.productGridPane.getGridPane(this.productService.getAll(),4));
                    this.productBox.setVisible(true);
                    this.addProductBox.setVisible(false);

                }
            }
        });
    }
}
