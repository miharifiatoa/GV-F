package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminArticleBoxController implements Initializable {
    @FXML private Label articleCodeLabel;
    @FXML private Label productTypeNameLabel;
    @FXML private Label articleQuantityLabel;
    @FXML private VBox articleInfoVBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ArticleEntity article){
        articleCodeLabel.setText(article.getCode());
        productTypeNameLabel.setText(article.getProductType().getName());
        articleQuantityLabel.setText(String.valueOf(article.getQuantity()));
        setArticleInfoVBox(article);
    }
    private void setArticleInfoVBox(ArticleEntity article){
        articleInfoVBox.setOnMouseClicked(event->{
            getDashboardLayoutScrollpane().setContent(getArticleStory(article));
        });
    }
    private ScrollPane getDashboardLayoutScrollpane(){
        return (ScrollPane) articleInfoVBox.getParent().getParent().getParent().getParent();
    }
    public StackPane getArticleStory(ArticleEntity article){
        FXMLLoader articleStoryLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/articleStory.fxml"));
        StackPane articleStory;
        try {
            articleStory = articleStoryLoader.load();
            ArticleStoryController articleStoryController = articleStoryLoader.getController();
            articleStoryController.initialize(article);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return articleStory;
    }
}
