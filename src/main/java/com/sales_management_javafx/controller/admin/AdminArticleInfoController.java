package com.sales_management_javafx.controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArrivalArticleEntity;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.ShareArticleEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminArticleInfoController implements Initializable {
    @FXML private VBox articleInfoVBox;
    @FXML private Label productTypeNameLabel;
    @FXML private Label articleQuantityLabel;
    @FXML private Label articlePriceLabel;
    @FXML private Label articleCodeLabel;
    @FXML private Label articleSizeLabel;
    @FXML private Label articleColorLabel;
    @FXML private Label productTypeMarkLabel;
    @FXML private Label productTypeReferenceLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ArrivalArticleEntity arrivalArticle){
        productTypeNameLabel.setText(arrivalArticle.getArticle().getProductType().getName());
        articleQuantityLabel.setText(String.valueOf(arrivalArticle.getQuantity()));
        articlePriceLabel.setText(String.valueOf(arrivalArticle.getArticle().getPrice()));
        articleCodeLabel.setText(String.valueOf(arrivalArticle.getArticle().getCode()));
        articleSizeLabel.setText(arrivalArticle.getArticle().getSize());
        articleColorLabel.setText(arrivalArticle.getArticle().getColor());
        productTypeMarkLabel.setText(arrivalArticle.getArticle().getProductType().getBrand());
        productTypeReferenceLabel.setText(arrivalArticle.getArticle().getProductType().getReference());
    }
    public void initialize(ShareArticleEntity shareArticle){
        productTypeNameLabel.setText(shareArticle.getArticle().getProductType().getName());
        articleQuantityLabel.setText(String.valueOf(shareArticle.getQuantity()));
        articlePriceLabel.setText(String.valueOf(shareArticle.getArticle().getPrice()));
        articleCodeLabel.setText(String.valueOf(shareArticle.getArticle().getCode()));
        articleSizeLabel.setText(shareArticle.getArticle().getSize());
        articleColorLabel.setText(shareArticle.getArticle().getColor());
        productTypeMarkLabel.setText(shareArticle.getArticle().getProductType().getBrand());
        productTypeReferenceLabel.setText(shareArticle.getArticle().getProductType().getReference());
    }
    public void initialize(SaleArticleEntity saleArticle){
        productTypeNameLabel.setText(saleArticle.getArticle().getProductType().getName());
        articleQuantityLabel.setText(String.valueOf(saleArticle.getQuantity()));
        articlePriceLabel.setText(String.valueOf(saleArticle.getArticle().getPrice()));
        articleCodeLabel.setText(String.valueOf(saleArticle.getArticle().getCode()));
        articleSizeLabel.setText(saleArticle.getArticle().getSize());
        articleColorLabel.setText(saleArticle.getArticle().getColor());
        productTypeMarkLabel.setText(saleArticle.getArticle().getProductType().getBrand());
        productTypeReferenceLabel.setText(saleArticle.getArticle().getProductType().getReference());
    }
}
