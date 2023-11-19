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
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ProductService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductBoxController implements Initializable {
    @FXML private StackPane productBox;
    @FXML private GridPane productBoxGridpane;
    @FXML private GridPane deleteBoxGridpane;
    @FXML private Label productNameLabel;
    @FXML private Label createProductTypeLabel;
    @FXML private ImageView deleteIcon;
    @FXML private Button delete;
    @FXML private Label save;
    @FXML private Label exit;
    private final ProductService productService;
    private final ProductGridPane productGridPane;

    public ProductBoxController() {
        this.productGridPane = new ProductGridPane();
        this.productService = new ProductService();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        productBoxGridpane.setVisible(true);
        deleteBoxGridpane.setVisible(false);
        this.setDelete();
        this.setExit();
    }
    private void setDelete(){
        delete.setOnAction(event->{
            productBoxGridpane.setVisible(false);
            deleteBoxGridpane.setVisible(true);
        });
    }
    private void setExit(){
        exit.setOnMouseClicked(event->{
            productBoxGridpane.setVisible(true);
            deleteBoxGridpane.setVisible(false);
        });
    }
    public void initialize(ProductEntity product){
        if (product.getProductTypes().isEmpty()){
            productNameLabel.setDisable(true);
        }
        productNameLabel.setText(product.getName());
        this.onShowProductTypes(product);
        this.onCreateProduct(product);

    }
    public StackPane getProductBoxLayout(){
        return (StackPane) productBox.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
    }
    private void onShowProductTypes(ProductEntity product){
        productNameLabel.setOnMouseClicked(event->{
            StackPane productLayout = this.getProductBoxLayout();
            BorderPane productBoxLayoutBorderpane = (BorderPane) productLayout.lookup("#productBoxLayoutBorderpane");
            ScrollPane productBoxLayoutScrollpane = (ScrollPane) productLayout.lookup("#productBoxLayoutScrollpane");
            GridPane productGridPane = new ProductTypeGridPane().getGridPane(this.productService.getById(product.getId()).getProductTypes(),4,false);
            FileIO.writeTo("product.dat",product);
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
}
