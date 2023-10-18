package com.sales_management_javafx.controller.article;

import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ArticleGridPane;
import com.sales_management_javafx.composent.ProductGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.service.ArticleService;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ArticleBoxController implements Initializable {
    @FXML
    private Label articleNameLabel;
    @FXML
    private Label articleCodeLabel;
    @FXML
    private StackPane articleBoxStackPane;
    private ScrollPane productBoxLayoutScrollpane;
    private final ArticleService articleService;
    private final ArticleGridPane articleGridPane;

    public ArticleBoxController() {
        this.articleGridPane = new ArticleGridPane();
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ArticleEntity article){
        articleNameLabel.setText(article.getLabel());
        this.onShowProducts(article);
    }
    private void onShowProducts(ArticleEntity article){
        articleBoxStackPane.setOnMouseClicked(event->{
            BorderPane borderPane = (BorderPane) articleBoxStackPane.getParent().getParent().getParent().getParent().getParent().getParent();
            BorderPane productBoxLayoutBorderpane = (BorderPane) borderPane.getCenter();
            this.productBoxLayoutScrollpane = (ScrollPane) productBoxLayoutBorderpane.getCenter();
            GridPane gridPane = (GridPane) articleBoxStackPane.getParent();
            if (!Objects.equals(articleBoxStackPane.getStyleClass().toString(),("active"))){
                for (Node node1 : gridPane.getChildren()){
                    node1.getStyleClass().remove("active");
                }
                articleBoxStackPane.getStyleClass().add("active");
                System.out.println(articleBoxStackPane.getStyleClass());
            }
            GridPane productGridPane = new ProductGridPane().getGridPane(this.articleService.getById(article.getId()).getProducts(),3,false);
            productGridPane.setId(String.valueOf(article.getId()));
            FileIO.writeTo("article.dat",article);
            productBoxLayoutScrollpane.setContent(productGridPane);
        });
    }
}
