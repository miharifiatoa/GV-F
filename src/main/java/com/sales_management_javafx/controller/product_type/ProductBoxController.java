package com.sales_management_javafx.controller.product_type;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ProductGridPane;
import com.sales_management_javafx.composent.ProductTypeGridPane;
import com.sales_management_javafx.controller.article.ProductTypeCreateController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.service.ProductService;
import org.sales_management.service.ProductTypeService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductBoxController implements Initializable {
    @FXML
    private StackPane productBox;
    @FXML
    private GridPane productInfo;
    @FXML
    private GridPane productSuppression;
    @FXML
    private ImageView editIcon;
    @FXML
    private ImageView deleteIcon;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label exit;
    @FXML
    private Label delete;
    @FXML
    private Button deleteProduct;
    @FXML private Label add;
    private final ProductTypeService productTypeService;
    private final ProductService productService;

    public ProductBoxController() {
        this.productService = new ProductService();
        this.productTypeService = new ProductTypeService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        this.productInfo.setVisible(true);
        this.productSuppression.setVisible(false);
        this.setExit();
        this.setDeleteProduct();
    }
    public void initialize(ProductTypeEntity product){
        productNameLabel.setText(product.getName());
        if (!product.getArticles().isEmpty()){
            deleteProduct.setDisable(true);
        }
        else {
            productNameLabel.setDisable(true);
        }
        this.showProductType(product);
        this.setDelete(product.getId());
        this.setAdd(product);
    }
    private void putIcons(){
        this.editIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/EditIcon.png"))));
        this.deleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
    }
    private void setDeleteProduct(){
        deleteProduct.setOnAction(event->{
            productSuppression.setVisible(true);
            productInfo.setVisible(false);
        });
    }
    private void setExit(){
        exit.setOnMouseClicked(event->{
            productSuppression.setVisible(false);
            productInfo.setVisible(true);
        });
    }
    private void setDelete(Long id){
        delete.setOnMouseClicked(event->{
            ProductTypeEntity product = productTypeService.deleteById(id);
            if (product != null){
                GridPane productGridpane = new ProductGridPane().getGridPane(productService.getById(product.getProductCategory().getId()).getProducts(),4,false);
                getProductBoxLayoutScrollpane().setContent(productGridpane);
            }
        });
    }
    private void showProductType(ProductTypeEntity productType){
        productNameLabel.setOnMouseClicked(event->{
            FileIO.writeTo("product.dat",productType);
            GridPane gridPane = new ProductTypeGridPane().getGridPane(productType.getArticles(),4);
            getProductBoxLayoutScrollpane().setContent(gridPane);
        });
    }
    private void setAdd(ProductTypeEntity product){
        add.setOnMouseClicked(event->{
            StackPane productBoxLayout = this.getProductBoxLayout();
            BorderPane modal = (BorderPane) productBoxLayout.lookup("#modal");
            modal.setCenter(this.getProductTypeCreate(product));
            modal.setVisible(true);
        });
    }
    private StackPane getProductTypeCreate(ProductTypeEntity product){
        FXMLLoader productTypeCreateLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_type/productTypeCreate.fxml"));
        StackPane productTypeCreate;
        try {
            productTypeCreate = productTypeCreateLoader.load();
            ProductTypeCreateController productTypeCreateController = productTypeCreateLoader.getController();
            productTypeCreateController.initialize(product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productTypeCreate;
    }
    public StackPane getProductBoxLayout(){
        return (StackPane) productBox.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
    }
    private ScrollPane getProductBoxLayoutScrollpane(){
        return (ScrollPane) productBox.getParent().getParent().getParent().getParent();
    }
}
