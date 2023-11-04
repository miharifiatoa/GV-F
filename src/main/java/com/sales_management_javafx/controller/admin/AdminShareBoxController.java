package com.sales_management_javafx.controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;
import org.sales_management.entity.ShareArticleEntity;
import org.sales_management.entity.ShareEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminShareBoxController implements Initializable {
    @FXML private StackPane shareBox;
    @FXML private VBox shareVBox;
    @FXML private Label articleNumbersLabel;
    @FXML private Label shareDateLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ShareEntity share){
        articleNumbersLabel.setText(share.getUser().getAccount().getUsername() + " a partagé " + getTotalSize(share) + " produit(s) vers le boutique : " + share.getShop().getName() + " à " + share.getShop().getAddress());
        shareDateLabel.setText(String.valueOf(share.getShareDate()));
    }
    private int getTotalSize(ShareEntity share){
        int total = 0;
        for (ShareArticleEntity shareArticle : share.getShareArticles()){
            total += shareArticle.getQuantity();
        }
        return total;
    }
}
