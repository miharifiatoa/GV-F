package com.sales_management_javafx.controller.article;

import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ArticleInfoGridPane;
import com.sales_management_javafx.composent.SellerArticleGridPane;
import com.sales_management_javafx.composent.StockistArticleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.ProductCategoryService;
import org.sales_management.service.ProductService;

import java.net.URL;
import java.util.Collection;
import java.util.Objects;
import java.util.ResourceBundle;

public class ArticleInfoController implements Initializable {
    @FXML private VBox articleInfoVBox;
    @FXML private Label productTypeNameLabel;
    @FXML private Label articleQuantityLabel;
    @FXML private Label articlePriceLabel;
    @FXML private Label articleCodeLabel;
    @FXML private Label articleSizeLabel;
    @FXML private Label articleColorLabel;
    @FXML private Label productTypeMarkLabel;
    @FXML private Label productTypeReferenceLabel;

    @FXML private Button remove;
    private final ProductCategoryService productCategoryService;
    private final ProductService productService;

    public ArticleInfoController() {
        this.productService = new ProductService();
        this.productCategoryService = new ProductCategoryService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ArticleEntity article){
        productTypeNameLabel.setText(article.getProductType().getName());
        articleQuantityLabel.setText(String.valueOf(article.getQuantity()));
        articlePriceLabel.setText(String.valueOf(article.getPrice()));
        articleCodeLabel.setText(String.valueOf(article.getCode()));
        articleSizeLabel.setText(article.getSize());
        articleColorLabel.setText(article.getColor());
        productTypeMarkLabel.setText(article.getProductType().getBrand());
        productTypeReferenceLabel.setText(article.getProductType().getReference());
        this.setRemove(article);
    }
    private void setRemove(ArticleEntity article){
        remove.setOnAction(event->{
            if (Objects.equals(getArticleLayoutScrollpane().getId(), "arrivalLayoutScrollpane")){
                Collection<ArticleEntity> arrivals = FileIO.readArticleFromFile("arrivals.dat");
                arrivals.remove(article);
                FileIO.writeTo("arrivals.dat",arrivals);
                GridPane arrivalArticleGridpane = new ArticleInfoGridPane().getGridPane(FileIO.readArticleFromFile("arrivals.dat"),2);
                GridPane stockistArticleGridPane = new StockistArticleGridPane().getGridPane(new ArticleService().getAll(), 4);
                ScrollPane stockistBoxLayoutScrollpane = (ScrollPane) getArticleLayoutScrollpane().getParent().getParent().getParent().getParent().lookup("#stockistBoxLayoutScrollpane");
                stockistBoxLayoutScrollpane.setContent(stockistArticleGridPane);
                getArticleLayoutScrollpane().setContent(arrivalArticleGridpane);
            }
            else if (Objects.equals(getArticleLayoutScrollpane().getId(),"shareArticleLayoutScrollpane")){
                Collection<ArticleEntity> shares = FileIO.readArticleFromFile("articles.dat");
                shares.remove(article);
                FileIO.writeTo("articles.dat",shares);
                GridPane shareArticleGridpane  = new ArticleInfoGridPane().getGridPane(FileIO.readArticleFromFile("articles.dat"),2);
                GridPane stockistArticleGridPane = new StockistArticleGridPane().getGridPane(new ArticleService().getAll(),4);
                ScrollPane stockistBoxLayoutScrollpane = (ScrollPane) getArticleLayoutScrollpane().getParent().getParent().getParent().getParent().lookup("#stockistBoxLayoutScrollpane");
                stockistBoxLayoutScrollpane.setContent(stockistArticleGridPane);
                getArticleLayoutScrollpane().setContent(shareArticleGridpane);
            }
            else if (Objects.equals(getArticleLayoutScrollpane().getId(),"pannierLayoutScrollpane")){
                Collection<ArticleEntity> articles = FileIO.readArticleFromFile("sales.dat");
                articles.remove(article);
                FileIO.writeTo("sales.dat",articles);
                GridPane pannierGridpane  = new ArticleInfoGridPane().getGridPane(FileIO.readArticleFromFile("sales.dat"),2);
                GridPane sellerArticleGridPane = new SellerArticleGridPane().getGridPane(new ArticleService().getAll(),4);
                ScrollPane sellerArticleScrollpane = (ScrollPane) getArticleLayoutScrollpane().getParent().getParent().lookup("#sellerArticleScrollpane");
                Label priceTotal = (Label) getArticleLayoutScrollpane().getParent().getParent().lookup("#priceTotal");
                priceTotal.setText("Prix total : " + FileIO.getPriceTotal("sales.dat") +"Ar");
                sellerArticleScrollpane.setContent(sellerArticleGridPane);
                getArticleLayoutScrollpane().setContent(pannierGridpane);
            }
            else {
                System.out.println("...");
            }
        });
    }
    private ScrollPane getArticleLayoutScrollpane(){
        return (ScrollPane) articleInfoVBox.getParent().getParent().getParent().getParent();
    }
    private StackPane getProductBoxLayout(){
        return (StackPane) getArticleLayoutScrollpane().getParent().getParent().getParent().getParent();
    }
    private ScrollPane getProductBoxLayoutScrollPane(){
        return (ScrollPane) getProductBoxLayout().lookup("#productBoxLayoutScrollpane");
    }
}
