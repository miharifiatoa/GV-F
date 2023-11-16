package com.sales_management_javafx.controller.article_type;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.sales_management.entity.ArrivalArticleEntity;
import org.sales_management.entity.ShareArticleEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class ArticleCodeController implements Initializable {
    @FXML private Label codeLabel;
    @FXML private Label quantityLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ShareArticleEntity shareArticle){
        quantityLabel.setText(String.valueOf(shareArticle.getQuantity()));
        codeLabel.setText(shareArticle.getArticleType().getArticle().getCode());
    }
    public void initialize(ArrivalArticleEntity arrivalArticle){
        quantityLabel.setText(String.valueOf(arrivalArticle.getQuantity()));
        codeLabel.setText(arrivalArticle.getArticleType().getArticle().getCode());
    }
}
