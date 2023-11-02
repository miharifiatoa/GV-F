package com.sales_management_javafx.controller.sale;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class FactureArticleBoxController implements Initializable {
    @FXML private Label productTypeNameLabel;
    @FXML private Label articlePriceLabel;
    @FXML private Label articleQuantityLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(SaleArticleEntity saleArticle){
        productTypeNameLabel.setText(saleArticle.getArticle().getProductType().getName());
        articlePriceLabel.setText(String.valueOf(saleArticle.getArticle().getPrice()));
        articleQuantityLabel.setText(String.valueOf(saleArticle.getQuantity()));

    }
}
