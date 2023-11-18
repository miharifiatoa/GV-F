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
        articleQuantityLabel.setText(String.valueOf(arrivalArticle.getQuantity()));
        articleSizeLabel.setText(arrivalArticle.getArticleType().getSize());
        articleColorLabel.setText(arrivalArticle.getArticleType().getColor());
    }
    public void initialize(ShareArticleEntity shareArticle){
        articleQuantityLabel.setText(String.valueOf(shareArticle.getQuantity()));
        articleSizeLabel.setText(shareArticle.getArticleType().getSize());
        articleColorLabel.setText(shareArticle.getArticleType().getColor());
    }
    public void initialize(SaleArticleEntity saleArticle){
        articleQuantityLabel.setText(String.valueOf(saleArticle.getQuantity()));
        articleSizeLabel.setText(saleArticle.getArticleType().getSize());
        articleColorLabel.setText(saleArticle.getArticleType().getColor());
    }
}
