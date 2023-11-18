package com.sales_management_javafx.controller.seller;

import com.sales_management_javafx.composent.SellerArticleTypeGridPane;
import com.sales_management_javafx.composent.StockistArticleTypeGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.ProductTypeService;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerArticleBoxController implements Initializable {
    @FXML private StackPane articleBox;
    @FXML private VBox articleVBox;
    @FXML private Label articlePrice;
    @FXML private Label productTypeNameLabel;
    @FXML private Label codeLabel;
    @FXML private Label articleQuantityLabel;
    private final ArticleService articleService;
    private final ProductTypeService productTypeService;

    public SellerArticleBoxController() {
        this.productTypeService = new ProductTypeService();
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ArticleEntity article){
        if (article.getArticleTypeEntities().isEmpty()){
            codeLabel.setDisable(true);
        }
        codeLabel.setText(article.getCode());
        articlePrice.setText(article.getPrice() + "Ar");
        productTypeNameLabel.setText(article.getProductTypeEntity().getName());
        articleQuantityLabel.setText(article.getArticleTypeEntities().size() + " types d' article");
    }
    public void initializeForSeller(ArticleEntity article){
        this.initialize(article);
        codeLabel.setOnMouseClicked(event->{
            GridPane sellerArticleTypeGridPane = new SellerArticleTypeGridPane().getGridPane(article.getArticleTypeEntities(),4);
            getProductBoxLayoutScrollpane().setContent(sellerArticleTypeGridPane);
        });
    }
    public void initializeForStockist(ArticleEntity article){
        this.initialize(article);
        codeLabel.setOnMouseClicked(event->{
            GridPane stockistArticleTypeGridPane = new StockistArticleTypeGridPane().getGridPane(article.getArticleTypeEntities(),4);
            getProductBoxLayoutScrollpane().setContent(stockistArticleTypeGridPane);
        });
    }
    private ScrollPane getProductBoxLayoutScrollpane(){
        return (ScrollPane) articleBox.getParent().getParent().getParent().getParent();
    }

}
