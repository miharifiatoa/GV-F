package com.sales_management_javafx.controller.product_variation;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.ProductGridPane;
import com.sales_management_javafx.composent.ProductShareGridPane;
import com.sales_management_javafx.composent.ProductVariationGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import org.sales_management.entity.PriceVariationEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.PriceVariationService;
import org.sales_management.service.ProductService;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class ProductVariationBoxController implements Initializable {
    @FXML
    private StackPane productVariationBox;
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
    private Label deleteText;
    @FXML
    private Button shareButton;
    @FXML
    private ScrollPane productScrollpane;
    @FXML
    private VBox productVariationVBox;
    @FXML
    private VBox deleteVBox;
    @FXML
    private VBox addVBox;
    @FXML
    private VBox shareVBox;
    @FXML
    private Button deleteButton;
    @FXML
    private Button confirmDeleteButton;
    @FXML
    private Button editButton;
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
    private VBox retireVBox;
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
    private final PriceVariationService priceVariationService;

    public ProductVariationBoxController() {
        this.priceVariationService = new PriceVariationService();
        this.articleService = new ArticleService();
        this.productService = new ProductService();
        this.productGridPane = new ProductGridPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.productVariationVBox.setVisible(true);
        this.deleteVBox.setVisible(false);
        this.addVBox.setVisible(false);
        this.retireVBox.setVisible(false);
        this.shareVBox.setVisible(false);
        this.onDeletePriceVariation();
        this.onExitDeleteProduct();
        this.onEditProductVariation();
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
    public void initialize(PriceVariationEntity priceVariation){
        if (priceVariation.getQuantity()<=0){
            retireProductButton.setDisable(true);
        }
        Double price = priceVariation.getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        this.priceLabel.setText(decimalFormat.format(price)+ " Ar");
        this.sizeLabel.setText(String.valueOf(priceVariation.getSize()));
        this.quantityLabel.setText(String.valueOf(priceVariation.getQuantity()));
        this.qualityLabel.setText(priceVariation.getQuality());
        this.brandLabel.setText(priceVariation.getBrand());
        this.referenceLabel.setText(priceVariation.getReference());
        this.colorLabel.setText(priceVariation.getColor());
        this.productVariationBox.getChildren().add(this.getProductVariationEdit(priceVariation));
        this.deleteText.setText("Voulez vous vraiment supprimer ce produit dans la liste du produit " + priceVariation.getProduct().getName() + "?");
        this.productQuantityRetireLabel.setText("max : " + priceVariation.getQuantity());
        this.productQuantityShareLabel.setText("max : " + priceVariation.getQuantity());
        this.onConfirmDeletePriceVariation(priceVariation.getId());
        this.onConfirmRetireProduct(priceVariation);
        this.onConfirmAddProduct(priceVariation);
        this.onAddPricesInShareList(priceVariation);
        NumberTextField.requireIntegerOnly(quantityRetireTextfield,priceVariation.getQuantity());
        NumberTextField.requireIntegerOnly(quantitySharedTextfield,priceVariation.getQuantity());
    }
//    private void onConfirmDeleteProduct(Long product_id){
//        this.confirmDeleteButton.setOnAction(actionEvent -> {
//            ProductEntity product = this.productService.deleteById(product_id);
//            if (product!=null){
//                this.refreshData(product);
//            }
//        });
//    }
    private void onDeletePriceVariation(){
        this.deleteButton.setOnAction(event->{
            this.productVariationVBox.setVisible(false);
            this.deleteVBox.setVisible(true);
        });
    }
    private void onConfirmDeletePriceVariation(Long id){
        confirmDeleteButton.setOnAction(event->{
            ScrollPane productBoxLayoutScrollpane = (ScrollPane) productVariationBox.getParent().getParent().getParent().getParent();
            GridPane gridPane = new ProductVariationGridPane().getGridPane(new PriceVariationService().getById(id).getProduct().getPriceVariations(), 3);
            productBoxLayoutScrollpane.setContent(gridPane);
        });
    }
    private void onExitDeleteProduct(){
        this.cancelDeleteButton.setOnAction(event->{
            this.productVariationVBox.setVisible(true);
            this.deleteVBox.setVisible(false);
        });
    }
    private void onEditProductVariation(){
        editButton.setOnAction(actionEvent -> {
            VBox editProductForm = (VBox) this.productVariationVBox.getParent().lookup("#productVariationEditVBox");
            editProductForm.setVisible(true);
            this.productVariationVBox.setVisible(false);
        });
    }
    private void onAddProduct(){
        addProductButton.setOnAction(event->{
            this.productVariationVBox.setVisible(false);
            this.addVBox.setVisible(true);
        });
    }
    private void onConfirmAddProduct(PriceVariationEntity priceVariation){
        confirmAddProductButton.setOnAction(event->{
            if (!this.quantityAddedTextfield.getText().isEmpty() && priceVariation!=null){
                priceVariation.setQuantity(priceVariation.getQuantity() + Integer.parseInt(quantityAddedTextfield.getText()));
                PriceVariationEntity priceResponse = this.priceVariationService.update(priceVariation);
                if (priceResponse!=null){
                    ScrollPane productBoxLayoutScrollpane = (ScrollPane) productVariationBox.getParent().getParent().getParent().getParent();
                    GridPane gridPane = new ProductVariationGridPane().getGridPane(new PriceVariationService().getById(priceVariation.getId()).getProduct().getPriceVariations(), 3);
                    productBoxLayoutScrollpane.setContent(gridPane);
                }
            }

        });
    }
    private void onExitAddProduct(){
        exitAddProductButton.setOnAction(event->{
            this.productVariationVBox.setVisible(true);
            this.addVBox.setVisible(false);
        });
    }
    private void onRetireProduct(){
        this.retireProductButton.setOnAction(event->{
            this.retireVBox.setVisible(true);
            this.productVariationVBox.setVisible(false);
        });
    }
    private void onConfirmRetireProduct(PriceVariationEntity priceVariation){
        this.confirmRetireProductButton.setOnAction(event->{
            if (!quantityRetireTextfield.getText().isEmpty() && priceVariation!=null){
                priceVariation.setQuantity(priceVariation.getQuantity() - Integer.parseInt(quantityRetireTextfield.getText()));
                PriceVariationEntity productResponse = this.priceVariationService.update(priceVariation);
                if (productResponse!=null){
                    ScrollPane productBoxLayoutScrollpane = (ScrollPane) productVariationBox.getParent().getParent().getParent().getParent();
                    GridPane gridPane = new ProductVariationGridPane().getGridPane(new PriceVariationService().getById(priceVariation.getId()).getProduct().getPriceVariations(), 3);
                    productBoxLayoutScrollpane.setContent(gridPane);
                }
            }
        });
    }
    private void onExitRetireProduct(){
        exitRetireProductButton.setOnAction(event->{
            this.retireVBox.setVisible(false);
            this.productVariationVBox.setVisible(true);
        });
    }
    private void onShareProduct(){
        this.shareButton.setOnAction(event->{
            this.shareVBox.setVisible(true);
            this.productVariationVBox.setVisible(false);
        });
    }
    private void onAddPricesInShareList(PriceVariationEntity priceVariation){
        addProductInShareListButton.setOnAction(event->{
            priceVariation.setQuantity(Integer.parseInt(quantitySharedTextfield.getText()));
            Collection<PriceVariationEntity> existingPrices = FileIO.readPricesFromFile("prices.dat");
            existingPrices.add(priceVariation);
            FileIO.writeTo("prices.dat", existingPrices);
            ScrollPane productBoxLayoutScrollpane = (ScrollPane) productVariationBox.getParent().getParent().getParent().getParent();
            GridPane gridPane = new ProductVariationGridPane().getGridPane(new PriceVariationService().getById(priceVariation.getId()).getProduct().getPriceVariations(), 3);
            BorderPane productBoxLayoutBorderpane = (BorderPane) productBoxLayoutScrollpane.getParent();
            StackPane shareProductLayoutStackpane = (StackPane) productBoxLayoutBorderpane.getBottom();
            ScrollPane shareProductLayoutScrollpane = (ScrollPane) shareProductLayoutStackpane.lookup("#shareProductLayoutScrollpane");
            if (shareProductLayoutScrollpane!=null){
                GridPane shareProductGridpane = new ProductShareGridPane().getGridPane(FileIO.readPricesFromFile("prices.dat"),1);
                shareProductLayoutScrollpane.setContent(shareProductGridpane);
            }
            productBoxLayoutScrollpane.setContent(gridPane);
        });
    }
    private void onExitShareProduct(){
        this.exitShareProductButton.setOnAction(event->{
            this.shareVBox.setVisible(false);
            this.productVariationVBox.setVisible(true);
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
    private VBox getProductVariationEdit(PriceVariationEntity priceVariation){
        FXMLLoader productVariationEditLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_variation/productVariationEdit.fxml"));
        VBox productVariationEditVbox;
        try {
            productVariationEditVbox = productVariationEditLoader.load();
            ProductVariationEditController productVariationEditController = productVariationEditLoader.getController();
            productVariationEditController.initialize(priceVariation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productVariationEditVbox;
    }
}





