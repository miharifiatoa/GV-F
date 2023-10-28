package com.sales_management_javafx.controller.product_category;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ProductCategoryGridPane;
import com.sales_management_javafx.composent.ProductGridPane;
import com.sales_management_javafx.controller.product.ProductCreateController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ProductCategoryEntity;
import org.sales_management.service.ProductCategoryService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductCategoryBoxController implements Initializable {
    @FXML
    private Label productCategoryNameLabel;
    @FXML
    private Label createProductCategoryLabel;
    @FXML
    private StackPane productCategoryBoxStackPane;
    private final ProductCategoryService productCategoryService;
    private final ProductCategoryGridPane productCategoryGridPane;

    public ProductCategoryBoxController() {
        this.productCategoryGridPane = new ProductCategoryGridPane();
        this.productCategoryService = new ProductCategoryService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ProductCategoryEntity productCategory){
        if (productCategory.getProducts().isEmpty()){
            productCategoryNameLabel.setDisable(true);
        }
        productCategoryNameLabel.setText(productCategory.getName());
        this.onShowProducts(productCategory);
        this.onCreateProduct(productCategory);

    }
    public StackPane getProductBoxLayout(){
        return (StackPane) productCategoryBoxStackPane.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
    }
    private void onShowProducts(ProductCategoryEntity productCategory){
        productCategoryNameLabel.setOnMouseClicked(event->{
            StackPane product = this.getProductBoxLayout();
            System.out.println(product);
            BorderPane productBoxLayoutBorderpane = (BorderPane) product.lookup("#productBoxLayoutBorderpane");
            ScrollPane productBoxLayoutScrollpane = (ScrollPane) product.lookup("#productBoxLayoutScrollpane");
//            HBox navigation = (HBox) product.lookup("#navigation");
            GridPane productGridPane = new ProductGridPane().getGridPane(this.productCategoryService.getById(productCategory.getId()).getProducts(),4,false);
            FileIO.writeTo("product_category.dat",productCategory);
            productBoxLayoutScrollpane.setContent(productGridPane);
            productBoxLayoutBorderpane.setCenter(productBoxLayoutScrollpane);
            productBoxLayoutBorderpane.setBottom(this.getToolbar());
//            navigation.getChildren().add(new Label(productCategory.getLabel()));
        });
    }
    private void onCreateProduct(ProductCategoryEntity productCategory){
        createProductCategoryLabel.setOnMouseClicked(event->{
            StackPane productBoxLayout = this.getProductBoxLayout();
            BorderPane modal = (BorderPane) productBoxLayout.lookup("#modal");
            modal.setCenter(this.getCreateProductBox(productCategory));
            modal.setVisible(true);
            event.consume();
        });
    }
    private StackPane getToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productToolbar.fxml"));
        StackPane toolbar;
        try {
            toolbar = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toolbar;
    }
    private StackPane getCreateProductBox(ProductCategoryEntity productCategory){
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
