package com.sales_management_javafx.controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.sales_management.entity.ArticleEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminArticleBoxController implements Initializable {
    @FXML private Label articleCodeLabel;
    @FXML private Label productTypeNameLabel;
    @FXML private Label articleQuantityLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ArticleEntity article){
        articleCodeLabel.setText(article.getCode());
        productTypeNameLabel.setText(article.getProductType().getName());
        articleQuantityLabel.setText(String.valueOf(article.getQuantity()));
    }
}
