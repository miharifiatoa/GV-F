package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.MenuIcon;
import com.sales_management_javafx.composent.ProductCategoryGridPane;
import com.sales_management_javafx.composent.MenuGridPane;
import com.sales_management_javafx.composent.ArticleGridPane;
import com.sales_management_javafx.composent.ProductGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ProductCategoryService;
import org.sales_management.service.ProductService;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProductBoxLayoutController implements Initializable {
    @FXML
    private StackPane productBoxLayout;
    @FXML
    private BorderPane product;
    @FXML
    private BorderPane modal;
    @FXML
    private ScrollPane productBoxLayoutScrollpane;
    @FXML
    private BorderPane productBoxLayoutBorderpane;
    @FXML
    private TextField searchProductTextfield;
    @FXML
    private Label productName;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final ProductGridPane productGridPane;
    private final ProductCategoryGridPane productCategoryGridPane;
    private final MenuGridPane menuGridPane;
    private final MenuIcon menuIcon;

    public ProductBoxLayoutController() {
        this.menuIcon = new MenuIcon();
        this.menuGridPane = new MenuGridPane();
        this.productCategoryService = new ProductCategoryService();
        this.productCategoryGridPane = new ProductCategoryGridPane();
        this.productService = new ProductService();
        this.productGridPane = new ProductGridPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setProductCategories();
        this.initializeSearchTextField();
        this.onShowProducts();
        this.product.setVisible(true);
        this.modal.setVisible(false);
        this.productBoxLayoutBorderpane.setBottom(this.getToolbar());
    }
    private void setProductCategories(){
        this.productBoxLayoutScrollpane.setContent(new ProductCategoryGridPane().getGridPane(productCategoryService.getAll(),4));
    }
    private void initializeSearchTextField(){
        this.searchProductTextfield.setPromptText("Recherche");
        searchProductTextfield.textProperty().addListener((observableValue, s, t1) -> {
            if (!searchProductTextfield.getText().isEmpty()){
                Collection<ProductEntity> products = this.productService.searchProductsByName(searchProductTextfield.getText());
                this.productBoxLayoutScrollpane.setContent(new ProductGridPane().getGridPane(products,4,false));
                System.out.println(productBoxLayoutScrollpane);
            }
            else {
                this.productBoxLayoutScrollpane.setContent(new ProductGridPane().getGridPane(this.productService.getAll(), 4,false));
            }
        });
    }
    private void onShowProducts(){
        productName.setOnMouseClicked(event->{
            this.setProductCategories();
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
}
