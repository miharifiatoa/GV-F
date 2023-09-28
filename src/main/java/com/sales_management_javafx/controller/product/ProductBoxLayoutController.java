package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.MenuIcon;
import com.sales_management_javafx.composent.ArticleGridPane;
import com.sales_management_javafx.composent.MenuGridPane;
import com.sales_management_javafx.composent.ProductGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.ProductService;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class ProductBoxLayoutController implements Initializable {
    @FXML
    private Button shareProductButton;
    @FXML
    private ScrollPane productBoxLayoutScrollpane;
    @FXML
    private BorderPane productBoxLayoutBorderpane;
    @FXML
    private ScrollPane articleBoxLayoutScrollpane;
    @FXML
    private TextField searchProductTextfield;
    private final ProductService productService;
    private final ArticleService articleService;
    private final ProductGridPane productGridPane;
    private final ArticleGridPane articleGridPane;
    private final MenuGridPane menuGridPane;
    private final MenuIcon menuIcon;

    public ProductBoxLayoutController() {
        this.menuIcon = new MenuIcon();
        this.menuGridPane = new MenuGridPane();
        this.articleService = new ArticleService();
        this.articleGridPane = new ArticleGridPane();
        this.productService = new ProductService();
        this.productGridPane = new ProductGridPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setArticles();
        this.setProducts();
        this.initializeSearchTextField();
        this.productBoxLayoutBorderpane.setBottom(this.getToolbar());
    }
    private void setProducts(){
        this.productBoxLayoutScrollpane.setContent(this.productGridPane.getGridPane(productService.getAll(),3,false));
        this.productBoxLayoutScrollpane.setFitToWidth(true);
    }
    private void setArticles(){
        articleBoxLayoutScrollpane.setContent(this.articleGridPane.getGridPane(this.articleService.getAll(),1));
        articleBoxLayoutScrollpane.setFitToWidth(true);
    }
    private void initializeSearchTextField(){
        this.searchProductTextfield.setPromptText("Recherche des produits");
        searchProductTextfield.textProperty().addListener((observableValue, s, t1) -> {
            if (!searchProductTextfield.getText().isEmpty()){
                Collection<ProductEntity> products = this.productService.searchProductsByName(searchProductTextfield.getText());
                this.productBoxLayoutScrollpane.setContent(new ProductGridPane().getGridPane(products,3,false));
                System.out.println(products);
            }
            else {
                this.productBoxLayoutScrollpane.setContent(new ProductGridPane().getGridPane(this.productService.getAll(), 3,false));
            }
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
