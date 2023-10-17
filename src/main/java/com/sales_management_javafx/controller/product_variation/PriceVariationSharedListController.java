package com.sales_management_javafx.controller.product_variation;

import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ProductShareGridPane;
import com.sales_management_javafx.composent.ProductVariationGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.PriceVariationEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.PriceVariationService;
import org.sales_management.service.ProductService;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class PriceVariationSharedListController implements Initializable {
    @FXML
    private VBox productShareBox;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label productQuantityLabel;
    @FXML
    private Label productPriceLabel;
    @FXML
    private Label productBrandLabel;
    @FXML
    private Label productReferenceLabel;
    @FXML
    private Label productSizeLabel;
    @FXML
    private Label productQualityLabel;
    @FXML
    private Button removeProductButton;
    private final ArticleService articleService;
    private final ProductService productService;

    public PriceVariationSharedListController() {
        this.productService = new ProductService();
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(PriceVariationEntity priceVariation){
        productNameLabel.setText(priceVariation.getProduct().getName());
        productQuantityLabel.setText(String.valueOf(priceVariation.getQuantity()));
        productPriceLabel.setText(String.valueOf(priceVariation.getPrice()));
        productBrandLabel.setText(priceVariation.getBrand());
        productQualityLabel.setText(priceVariation.getQuality());
        productReferenceLabel.setText(priceVariation.getReference());
        productSizeLabel.setText(priceVariation.getSize());
        this.onRemoveProductInShareList(priceVariation);
    }
    private void onRemoveProductInShareList(PriceVariationEntity priceVariation){
        removeProductButton.setOnAction(event->{
            Collection<PriceVariationEntity> products = FileIO.readPricesFromFile("prices.dat");
            products.remove(priceVariation);
            FileIO.writeTo("prices.dat",products);
            ScrollPane shareProductLayoutScrollpane = (ScrollPane) productShareBox.getParent().getParent().getParent().getParent();
            GridPane gridPane = new ProductShareGridPane().getGridPane(FileIO.readPricesFromFile("prices.dat"),1);
            BorderPane productBoxLayoutBorderpane = (BorderPane) shareProductLayoutScrollpane.getParent().getParent().getParent();
            ScrollPane productBoxLayoutScrollpane = (ScrollPane) productBoxLayoutBorderpane.getCenter();
            GridPane productVariationGridpane = new ProductVariationGridPane().getGridPane(new PriceVariationService().getById(priceVariation.getId()).getProduct().getPriceVariations(), 3);
            shareProductLayoutScrollpane.setContent(gridPane);
            productBoxLayoutScrollpane.setContent(productVariationGridpane);
        });
    }
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
