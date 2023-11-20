package com.sales_management_javafx.controller.article_type;

import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ArticleInfoGridPane;
import com.sales_management_javafx.composent.SellerArticleTypeGridPane;
import com.sales_management_javafx.composent.stockist.StockistArticleTypeGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleTypeEntity;
import org.sales_management.service.ArticleTypeService;
import org.sales_management.service.ProductService;
import org.sales_management.service.ProductTypeService;

import java.net.URL;
import java.util.Collection;
import java.util.Objects;
import java.util.ResourceBundle;

public class ArticleTypeInfoController implements Initializable {
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
    private final ProductService productService;
    private final ProductTypeService productTypeService;

    public ArticleTypeInfoController() {
        this.productTypeService = new ProductTypeService();
        this.productService = new ProductService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ArticleTypeEntity articleType){
        productTypeNameLabel.setText(articleType.getArticle().getProductTypeEntity().getName());
        articleQuantityLabel.setText(String.valueOf(articleType.getQuantity()));
        articlePriceLabel.setText(String.valueOf(articleType.getArticle().getPrice()));
        articleCodeLabel.setText(String.valueOf(articleType.getArticle().getCode()));
        articleSizeLabel.setText(articleType.getSize());
        articleColorLabel.setText(articleType.getColor());
        productTypeMarkLabel.setText(articleType.getArticle().getProductTypeEntity().getBrand());
        productTypeReferenceLabel.setText(articleType.getArticle().getProductTypeEntity().getReference());
        this.setRemove(articleType);
    }
    private void setRemove(ArticleTypeEntity article){
        remove.setOnAction(event->{
            if (Objects.equals(getArticleLayoutScrollpane().getId(), "arrivalLayoutScrollpane")){
                Collection<ArticleTypeEntity> arrivals = FileIO.readArticleFromFile("arrivals.dat");
                arrivals.remove(article);
                FileIO.writeTo("arrivals.dat",arrivals);
                GridPane arrivalArticleGridpane = new ArticleInfoGridPane().getGridPane(FileIO.readArticleFromFile("arrivals.dat"),2);
                GridPane stockistArticleGridPane = new StockistArticleTypeGridPane().getGridPane(new ArticleTypeService().getAll(), 4);
                ScrollPane stockistBoxLayoutScrollpane = (ScrollPane) getArticleLayoutScrollpane().getParent().getParent().getParent().getParent().lookup("#stockistBoxLayoutScrollpane");
                stockistBoxLayoutScrollpane.setContent(stockistArticleGridPane);
                getArticleLayoutScrollpane().setContent(arrivalArticleGridpane);
            }
            else if (Objects.equals(getArticleLayoutScrollpane().getId(),"shareArticleLayoutScrollpane")){
                Collection<ArticleTypeEntity> shares = FileIO.readArticleFromFile("shares.dat");
                shares.remove(article);
                FileIO.writeTo("shares.dat",shares);
                GridPane shareArticleGridpane  = new ArticleInfoGridPane().getGridPane(FileIO.readArticleFromFile("articles.dat"),2);
                GridPane stockistArticleGridPane = new StockistArticleTypeGridPane().getGridPane(new ArticleTypeService().getAll(),4);
                ScrollPane stockistBoxLayoutScrollpane = (ScrollPane) getArticleLayoutScrollpane().getParent().getParent().getParent().getParent().lookup("#stockistBoxLayoutScrollpane");
                stockistBoxLayoutScrollpane.setContent(stockistArticleGridPane);
                getArticleLayoutScrollpane().setContent(shareArticleGridpane);
            }
            else if (Objects.equals(getArticleLayoutScrollpane().getId(),"pannierLayoutScrollpane")){
                Collection<ArticleTypeEntity> articles = FileIO.readArticleFromFile("sales.dat");
                articles.remove(article);
                FileIO.writeTo("sales.dat",articles);
                GridPane pannierGridpane  = new ArticleInfoGridPane().getGridPane(FileIO.readArticleFromFile("sales.dat"),2);
                GridPane sellerArticleGridPane = new SellerArticleTypeGridPane().getGridPane(new ArticleTypeService().getAll(),4);
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
