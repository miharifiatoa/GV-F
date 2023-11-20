package com.sales_management_javafx.controller.product_category;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ProductGridPane;
import com.sales_management_javafx.controller.product.ProductCreateController;
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
import org.sales_management.entity.ProductCategoryEntity;
import org.sales_management.service.ProductCategoryService;
import org.sales_management.service.ProductService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.VBox;

public class ProductCategoryBoxController implements Initializable {
    @FXML private StackPane productCategoryBox;
    @FXML private GridPane productCategoryBoxGridpane;
    @FXML private GridPane deleteBoxGridpane;
    @FXML private Label productCategoryNameLabel;
    @FXML private Label createProductLabel;
    @FXML private ImageView deleteIcon;
    @FXML private Button delete;
    @FXML private Label save;
    @FXML private Label exit;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final ProductGridPane productGridPane;

    public ProductCategoryBoxController() {
        this.productCategoryService = new ProductCategoryService();
        this.productGridPane = new ProductGridPane();
        this.productService = new ProductService();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        productCategoryBoxGridpane.setVisible(true);
        deleteBoxGridpane.setVisible(false);
        this.setDelete();
        this.setExit();
    }
    private void setDelete(){
        delete.setOnAction(event->{
            productCategoryBoxGridpane.setVisible(false);
            deleteBoxGridpane.setVisible(true);
        });
    }
    private void setExit(){
        exit.setOnMouseClicked(event->{
            productCategoryBoxGridpane.setVisible(true);
            deleteBoxGridpane.setVisible(false);
        });
    }
    public void initialize(ProductCategoryEntity productCategory){
        if (!productCategory.getProducts().isEmpty()){
            delete.setDisable(true);
        }
        else {
            productCategoryNameLabel.setDisable(true);
        }
        productCategoryNameLabel.setText(productCategory.getName());
        this.onShowProducts(productCategory);
        this.onCreateProduct(productCategory);

    }
    public StackPane getProductBoxLayout(){
        return (StackPane) productCategoryBox.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
    }
    private void onShowProducts(ProductCategoryEntity productCategory){
        productCategoryNameLabel.setOnMouseClicked(event->{
            StackPane productLayout = this.getProductBoxLayout();
            BorderPane productBoxLayoutBorderpane = (BorderPane) productLayout.lookup("#productBoxLayoutBorderpane");
            ScrollPane productBoxLayoutScrollpane = (ScrollPane) productLayout.lookup("#productBoxLayoutScrollpane");
            GridPane productGridPane = new ProductGridPane().getGridPane(this.productCategoryService.getById(productCategory.getId()).getProducts(),4);
            FileIO.writeTo("product_category.dat",productCategory);
            productBoxLayoutScrollpane.setContent(productGridPane);
            productBoxLayoutBorderpane.setCenter(productBoxLayoutScrollpane);
        });
    }
    private void onCreateProduct(ProductCategoryEntity productCategory){
        createProductLabel.setOnMouseClicked(event->{
            StackPane productBoxLayout = this.getProductBoxLayout();
            BorderPane modal = (BorderPane) productBoxLayout.lookup("#modal");
            modal.setCenter(this.getProductCreateBox(productCategory));
            modal.setVisible(true);
            event.consume();
        });
    }
    private void putIcons(){
        this.deleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
    }
    private StackPane getProductCreateBox(ProductCategoryEntity productCategory){
        FXMLLoader createProductBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productCreate.fxml"));
        StackPane createProductBox;
        try {
            createProductBox = createProductBoxLoader.load();
            ProductCreateController productCreateController = createProductBoxLoader.getController();
            productCreateController.initialize(productCategory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return createProductBox;
    }
}
