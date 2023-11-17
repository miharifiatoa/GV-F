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
import javafx.scene.layout.VBox;
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.service.ProductService;
import org.sales_management.service.ProductTypeService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class ProductBoxController implements Initializable {
    @FXML private StackPane productBox;
    @FXML private VBox productInfoVBox;
    @FXML private VBox deleteVBox;
    @FXML private ImageView editIcon;
    @FXML private ImageView deleteIcon;
    @FXML private Label productNameLabel;
    @FXML private Button exit;
    @FXML private Button delete;
    @FXML private Label productTypeBrandLabel;
    @FXML private Label productTypeReferenceLabel;
    @FXML private Button save;
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
        this.productInfoVBox.setVisible(true);
        this.deleteVBox.setVisible(false);
        this.setExit();
        this.setDeleteProduct();
    }
    public void initialize(ProductTypeEntity productType){
        productNameLabel.setText(productType.getName());
        productTypeBrandLabel.setText(productType.getBrand());
        productTypeReferenceLabel.setText(productType.getReference());
        if (!productType.getArticles().isEmpty()){
            delete.setDisable(true);
        }
        else {
            productNameLabel.setDisable(true);
        }
        this.showProductType(productType);
        this.setDelete(productType.getId());
        this.setAdd(productType);
    }
    private void putIcons(){
        this.editIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/EditIcon.png"))));
        this.deleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
    }
    private void setDeleteProduct(){
        delete.setOnAction(event->{
            deleteVBox.setVisible(true);
            productInfoVBox.setVisible(false);
        });
    }
    private void setExit(){
        exit.setOnMouseClicked(event->{
            deleteVBox.setVisible(false);
            productInfoVBox.setVisible(true);
        });
    }
    private void setDelete(Long id){
        save.setOnAction(event->{
            ProductTypeEntity product = productTypeService.deleteById(id);
            if (product != null){
                GridPane productGridpane = new ProductGridPane().getGridPane(productService.getById(product.getProduct().getId()).getProductTypes(),4,false);
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
        FXMLLoader productTypeCreateLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article/productTypeCreate.fxml"));
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
