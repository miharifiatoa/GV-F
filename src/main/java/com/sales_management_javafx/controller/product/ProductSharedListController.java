package com.sales_management_javafx.controller.product;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.sales_management.service.ArticleService;
import org.sales_management.service.ProductService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductSharedListController implements Initializable {
    @FXML
    private VBox productShareBox;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label productQuantityLabel;
    @FXML
    private Label productPriceLabel;
    @FXML
    private Button removeProductButton;
    private final ArticleService articleService;
    private final ProductService productService;

    public ProductSharedListController() {
        this.productService = new ProductService();
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
//    public void initialize(ProductEntity product){
//        productNameLabel.setText(product.getName());
//        productQuantityLabel.setText(String.valueOf(product.getQuantity()));
//        productPriceLabel.setText(String.valueOf(product.getPrice()));
//        this.onRemoveProductInShareList(product);
//    }
//    private void onRemoveProductInShareList(ProductEntity product){
//        removeProductButton.setOnAction(event->{
//            Collection<ProductEntity> products = ProductFile.readProductsFromFile();
//            products.remove(product);
//            ProductFile.writeProductsToFile(products);
//            this.refreshData(product);
//        });
//    }
//    private void refreshData(ProductEntity product){
//        ScrollPane shareProductLayoutScrollpane = (ScrollPane) productShareBox.getParent().getParent().getParent().getParent();
//        BorderPane productBoxLayoutBorderpane = (BorderPane) shareProductLayoutScrollpane.getParent().getParent().getParent();
//        ScrollPane productBoxLayoutScrollpane = (ScrollPane) productBoxLayoutBorderpane.getCenter();
//        GridPane gridPane = (GridPane) productBoxLayoutScrollpane.getContent();
//        GridPane productShareGridPane = new ProductShareGridPane().getGridPane(ProductFile.readProductsFromFile(),2);
//        shareProductLayoutScrollpane.setContent(productShareGridPane);
//        try {
//            Long article_id = Long.valueOf(gridPane.getId());
//            Collection<ProductEntity> products = this.articleService.getById(article_id).getProducts();
//            GridPane productGridPane = new ProductGridPane().getGridPane(products, 3,true);
//            productGridPane.setId(String.valueOf(product.getArticle().getId()));
//            productBoxLayoutScrollpane.setContent(productGridPane);
//        }
//        catch (NumberFormatException e){
//            Collection<ProductEntity> products = this.productService.getAll();
//            GridPane productGridPane = new ProductGridPane().getGridPane(products,3,false);
//            productBoxLayoutScrollpane.setContent(productGridPane);
//        }
//    }
}
