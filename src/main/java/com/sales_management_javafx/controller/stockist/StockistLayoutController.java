package com.sales_management_javafx.controller.stockist;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.stockist.StockistArticleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.ArticleTypeService;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class StockistLayoutController implements Initializable {
    @FXML private ScrollPane stockistBoxLayoutScrollpane;
    @FXML private BorderPane stockistLayout;
    @FXML private BorderPane modal;
    @FXML private TextField searchArticleTextfield;
    private final ArticleTypeService articleTypeService;
    private final ArticleService articleService;

    public StockistLayoutController() {
        this.articleService = new ArticleService();
        this.articleTypeService = new ArticleTypeService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modal.setVisible(false);
        stockistLayout.setBottom(this.getToolbar());
        this.setArticles();
        this.setSearchArticleTextfield();
    }
    private void setArticles(){
        GridPane articleGridpane = new StockistArticleGridPane().getGridPane(articleService.getAll(),4);
        stockistBoxLayoutScrollpane.setContent(articleGridpane);
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
    private void setSearchArticleTextfield(){
        searchArticleTextfield.textProperty().addListener(event->{
            if (!searchArticleTextfield.getText().isEmpty()){
                Collection<ArticleEntity> articles = articleService.searchArticleByCode(searchArticleTextfield.getText());
                GridPane stockistArticleGridPane = new StockistArticleGridPane().getGridPane(articles,4);
                stockistBoxLayoutScrollpane.setContent(stockistArticleGridPane);
            }
            else {
                GridPane stockistArticleGridPane = new StockistArticleGridPane().getGridPane(articleService.getAll(),4);
                stockistBoxLayoutScrollpane.setContent(stockistArticleGridPane);
            }
        });
    }
}
