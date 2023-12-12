package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArticleTypeEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminArticleTypeBoxController implements Initializable {
    @FXML private Label sizeLabel;
    @FXML private Label quantityLabel;
    @FXML private Label colorLabel;
    @FXML private StackPane articleTypeBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ArticleTypeEntity articleType){
        colorLabel.setText(articleType.getColor());
        sizeLabel.setText(articleType.getSize());
        quantityLabel.setText(articleType.getQuantity() + " reste(s)");
        this.setArticleTypeBox(articleType);
    }
    private void setArticleTypeBox(ArticleTypeEntity articleType){
        articleTypeBox.setOnMouseClicked(event->{
            ScrollPane scrollPane = (ScrollPane) articleTypeBox.getParent().getParent().getParent().getParent();
            scrollPane.setContent(getArticleStory(articleType));
        });
    }
    public StackPane getArticleStory(ArticleTypeEntity articleType){
        FXMLLoader articleStoryLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/articleStory.fxml"));
        StackPane articleStory;
        try {
            articleStory = articleStoryLoader.load();
            ArticleStoryController articleStoryController = articleStoryLoader.getController();
            articleStoryController.initialize(articleType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return articleStory;
    }
}
