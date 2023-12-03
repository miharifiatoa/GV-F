package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ProductGridPane;
import com.sales_management_javafx.composent.ProductTypeGridPane;
import com.sales_management_javafx.controller.product_type.ProductTypeCreateController;
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
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ProductService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.sales_management.service.ProductCategoryService;

public class ProductBoxController implements Initializable {
    @FXML private StackPane productBox;
    @FXML private VBox productVBox;
    @FXML private VBox deleteVBox;
    @FXML private Label productNameLabel;
    @FXML private Label createProductTypeLabel;
    @FXML private ImageView deleteIcon;
    @FXML private ImageView editIcon;
    @FXML private Button delete;
    @FXML private Button edit;
    @FXML private Label save;
    @FXML private Label exit;
    @FXML private Label quantityLabel;
    @FXML private Label deleteText;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    public ProductBoxController() {
        this.productCategoryService = new ProductCategoryService();
        this.productService = new ProductService();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        productVBox.setVisible(true);
        deleteVBox.setVisible(false);
        this.setDelete();
        this.setExit();
        this.setEdit();
    }
    public void initialize(ProductEntity product){
        if (!product.getProductTypes().isEmpty()){
            delete.setDisable(true);
        }
        else {
            productNameLabel.setDisable(true);
        }
        productNameLabel.setText(product.getName());
        this.onShowProductTypes(product);
        this.onCreateProduct(product);
        this.setSave(product);
        this.quantityLabel.setText(product.getProductTypes().size() + " type(s)");
        this.deleteText.setText("Voulez vous vraiment supprimer ce produit : " + product.getName() + " ?");
        this.productBox.getChildren().add(this.getProductEdit(product));
    }
    private void setEdit(){
        edit.setOnAction(event->{
            productVBox.setVisible(false);
            productBox.lookup("#editVBox").setVisible(true);
        });
    }
    private void setDelete(){
        delete.setOnAction(event->{
            productVBox.setVisible(false);
            deleteVBox.setVisible(true);
        });
    }
    private void setExit(){
        exit.setOnMouseClicked(event->{
            productVBox.setVisible(true);
            deleteVBox.setVisible(false);
        });
    }
    public StackPane getProductBoxLayout(){
        return (StackPane) productBox.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
    }
    private ScrollPane getProductBoxLayoutScrollpane(){
        return (ScrollPane) productBox.getParent().getParent().getParent().getParent();
    }
    private void setSave(ProductEntity product){
        save.setOnMouseClicked(event->{
            if (productService.deleteById(product.getId()) != null){
                GridPane productGridpane = new ProductGridPane().getGridPane(productCategoryService.getById(product.getProductCategory().getId()).getProducts(),4);
                getProductBoxLayoutScrollpane().setContent(productGridpane);
            }
        });
    }
    private void onShowProductTypes(ProductEntity product){
        productNameLabel.setOnMouseClicked(event->{
            StackPane productLayout = this.getProductBoxLayout();
            BorderPane productBoxLayoutBorderpane = (BorderPane) productLayout.lookup("#productBoxLayoutBorderpane");
            ScrollPane productBoxLayoutScrollpane = (ScrollPane) productLayout.lookup("#productBoxLayoutScrollpane");
            GridPane productGridPane = new ProductTypeGridPane().getGridPane(this.productService.getById(product.getId()).getProductTypes(),4,false);
            productBoxLayoutScrollpane.setContent(productGridPane);
            productBoxLayoutBorderpane.setCenter(productBoxLayoutScrollpane);
        });
    }
    private void onCreateProduct(ProductEntity product){
        createProductTypeLabel.setOnMouseClicked(event->{
            StackPane productBoxLayout = this.getProductBoxLayout();
            BorderPane modal = (BorderPane) productBoxLayout.lookup("#modal");
            modal.setCenter(this.getProductTypeCreateBox(product));
            modal.setVisible(true);
            event.consume();
        });
    }
    private void putIcons(){
        this.deleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
        this.editIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/EditIcon.png"))));
    }
    private StackPane getProductTypeCreateBox(ProductEntity product){
        FXMLLoader createProductBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_type/productTypeCreate.fxml"));
        StackPane createProductBox;
        try {
            createProductBox = createProductBoxLoader.load();
            ProductTypeCreateController productTypeCreateController = createProductBoxLoader.getController();
            productTypeCreateController.initialize(product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return createProductBox;
    }
    private VBox getProductEdit(ProductEntity product){
        FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productEdit.fxml"));
        VBox vBox;
        try {
            vBox = fxmlLoader.load();
            ProductEditController productEditController = fxmlLoader.getController();
            productEditController.initialize(product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return vBox;
    }
}
