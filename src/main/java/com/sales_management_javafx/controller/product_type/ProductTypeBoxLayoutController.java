package com.sales_management_javafx.controller.product_type;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.MenuIcon;
import com.sales_management_javafx.composent.*;
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
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.service.*;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.ProductCategoryEntity;
import org.sales_management.entity.ProductEntity;

public class ProductTypeBoxLayoutController implements Initializable {
    @FXML private StackPane productBoxLayout;
    @FXML private BorderPane product;
    @FXML private BorderPane modal;
    @FXML private ScrollPane productBoxLayoutScrollpane;
    @FXML private BorderPane productBoxLayoutBorderpane;
    @FXML private TextField searchProductTextfield;
    @FXML private Label productTypes;
    @FXML private Label products;
    @FXML private Label articleTypes;
    @FXML private Label articles;
    @FXML private Label categories;
    @FXML private Label newCategoryLabel;
    @FXML private ImageView articleIcon;
    @FXML private Button exit;
    private final ProductTypeService productTypeService;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final ArticleService articleService;
    private final ArticleTypeService articleTypeService;
    private final ProductTypeGridPane productTypeGridPane;
    private final MenuGridPane menuGridPane;
    private final MenuIcon menuIcon;

    public ProductTypeBoxLayoutController() {
        this.productCategoryService = new ProductCategoryService();
        this.articleService = new ArticleService();
        this.articleTypeService = new ArticleTypeService();
        this.menuIcon = new MenuIcon();
        this.menuGridPane = new MenuGridPane();
        this.productService = new ProductService();
        this.productTypeService = new ProductTypeService();
        this.productTypeGridPane = new ProductTypeGridPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initializeSearchTextField();
        this.product.setVisible(true);
        this.modal.setVisible(false);
        this.putIcons();
        this.setProductCategories();
        this.setProducts();
        this.setProductTypes();
        this.setArticles();
        this.setArticleTypes();
        this.onCreateProductCategory();
        this.exit.setOnAction(event->{
        this.setExit();
        });
    }
    
    private void initializeSearchTextField(){
            this.searchProductTextfield.setPromptText("Recherche");
            searchProductTextfield.textProperty().addListener((observableValue, s, t1) -> {
            System.out.println("id = "+searchProductTextfield.getId());
            if (!searchProductTextfield.getText().isEmpty()){
                
                        switch (searchProductTextfield.getId()) {
                            case "category" -> {
                                Collection<ProductCategoryEntity> productCategory = this.productCategoryService.searchCategoryByName(searchProductTextfield.getText());
                                this.productBoxLayoutScrollpane.setContent(new ProductCategoryGridPane().getGridPane(productCategory,4));
                            }
                            case "product" -> {
                                Collection<ProductEntity> productEntity = this.productService.searchProductsByName(searchProductTextfield.getText());
                                this.productBoxLayoutScrollpane.setContent(new ProductGridPane().getGridPane(productEntity,4));
                            }
                            case "productType" -> {
                                Collection<ProductTypeEntity> types = this.productTypeService.searchProductsByName(searchProductTextfield.getText());
                                this.productBoxLayoutScrollpane.setContent(new ProductTypeGridPane().getGridPane(types,4,false));
                            }
                            case "article" -> {
                                Collection<ArticleEntity> article = this.articleService.searchArticleByCode(searchProductTextfield.getText());
                                this.productBoxLayoutScrollpane.setContent(new ArticleGridPane().getGridPane(article,4));
                            }
                            default -> {
                            }
                        }
            }
            else {
                    String id = searchProductTextfield.getId();
                    switch (id) {
                    case "category" -> {
                        Collection<ProductCategoryEntity> productCategory = this.productCategoryService.searchCategoryByName(searchProductTextfield.getText());
                        this.productBoxLayoutScrollpane.setContent(new ProductCategoryGridPane().getGridPane(productCategory,4));
                    }
                
                    case "product" -> {
                        Collection<ProductEntity> productEntity = this.productService.searchProductsByName(searchProductTextfield.getText());
                        this.productBoxLayoutScrollpane.setContent(new ProductGridPane().getGridPane(productEntity,4));
                    }
                    
                    case "productType" -> {
                        Collection<ProductTypeEntity> products = this.productTypeService.searchProductsByName(searchProductTextfield.getText());
                        this.productBoxLayoutScrollpane.setContent(new ProductTypeGridPane().getGridPane(products,4,false));
                    }
                    
                    case "article" -> {
                        Collection<ArticleEntity> articles = this.articleService.searchArticleByCode(searchProductTextfield.getText());
                        this.productBoxLayoutScrollpane.setContent(new ArticleGridPane().getGridPane(articles,4));
                    }   
                }
            }
        });        
    }
    
    private void setProductCategories(){
        categories.setOnMouseClicked(event->{
            this.productBoxLayoutScrollpane.setContent(new ProductCategoryGridPane().getGridPane(productCategoryService.getAll(),4));
            searchProductTextfield.setId("category");
        });
    }
    private void setProducts(){
        products.setOnMouseClicked(event->{
            this.productBoxLayoutScrollpane.setContent(new ProductGridPane().getGridPane(productService.getAll(),4));
            searchProductTextfield.setId("product");
        });
    }
    private void setProductTypes(){
        productTypes.setOnMouseClicked(event->{
            this.productBoxLayoutScrollpane.setContent(new ProductTypeGridPane().getGridPane(productTypeService.getAll(),4,false));
            searchProductTextfield.setId("productType");
        });
    }
    private void setArticles(){
        articles.setOnMouseClicked(event->{
            this.productBoxLayoutScrollpane.setContent(new ArticleGridPane().getGridPane(articleService.getAll(),4));
            searchProductTextfield.setId("article");
        });
    }
    private void setArticleTypes(){
        articleTypes.setOnMouseClicked(event->{
            this.productBoxLayoutScrollpane.setContent(new ArticleTypeGridPane().getGridPane(articleTypeService.getAll(),4,false));
            searchProductTextfield.setId("articleType");
        });
    }
    public void setExit(){
        productBoxLayout.getParent().setVisible(false);
        BorderPane stockistLayout = (BorderPane) productBoxLayout.getParent().getParent().getParent();
        stockistLayout.setBottom(getToolbar());
    }
    private void putIcons(){
        this.articleIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ArticleIcon.png"))));
    }
    
    
    private void onCreateProductCategory(){
        newCategoryLabel.setOnMouseClicked(event->{
            BorderPane modal = (BorderPane) productBoxLayout.lookup("#modal");
            modal.setCenter(this.getProductCategoryCreateBox());
            modal.setVisible(true);
            event.consume();
        });
    }
    
    private StackPane getProductCategoryCreateBox(){
        FXMLLoader createProductCategoryBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_category/productCategoryCreate.fxml"));
        StackPane createProductCategoryBox;
        try {
            createProductCategoryBox = createProductCategoryBoxLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return createProductCategoryBox;
    }

    private StackPane getToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/stockist/stockistToolbar.fxml"));
        StackPane toolbar;
        try {
            toolbar = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toolbar;
    }
}
